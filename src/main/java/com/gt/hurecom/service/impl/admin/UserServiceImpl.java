package com.gt.hurecom.service.impl.admin;

import com.gt.hurecom.dto.admin.UserRequest;
import com.gt.hurecom.dto.admin.UserResponse;
import com.gt.hurecom.dto.admin.UserSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.Role;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.admin.UserMapper;
import com.gt.hurecom.repository.admin.UserRepository;
import com.gt.hurecom.service.admin.UserService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.specification.admin.UserSpecification;
import com.gt.hurecom.utility.CommonUtils;
import com.gt.hurecom.utility.PageUtils;
import com.gt.hurecom.utility.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final ReferenceDataService referenceDataService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, ReferenceDataService referenceDataService) {
        super();
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.referenceDataService = referenceDataService;
    }

    @Override
    public UserResponse createUser(UserRequest request) throws HurecomException {
        log.debug("Service :: createUser :: Entered");

        if (userRepository.existsByEmailIgnoreCaseOrMobileNumber(request.getEmail(), request.getMobileNumber())) {
            CommonUtils.throwBusinessException("Email or Mobile number already exists.");
        }

        Organization organization = referenceDataService.getOrganizationById(request.getOrganizationId());
        Role role = referenceDataService.getRoleById(request.getRoleId());

        User user = userMapper.convertToEntity(request);
        user.setOrganization(organization);
        user.setRole(role);
        User savedEntity = userRepository.save(user);

        log.debug("Service :: createUser :: Exited");
        return userMapper.convertToResponse(savedEntity);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) throws HurecomException {
        log.debug("Service :: updateUser :: Entered");

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new HurecomException("User not found."));

        if (!existingUser.getEmail().equalsIgnoreCase(request.getEmail())
                && userRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            CommonUtils.throwBusinessException("Email already exists.");
        }

        if (!existingUser.getMobileNumber().equalsIgnoreCase(request.getMobileNumber())
                && userRepository.existsByMobileNumberAndIdNot(request.getMobileNumber(), id)) {
            CommonUtils.throwBusinessException("Mobile number already exists.");
        }

        userMapper.updateFromRequest(request, existingUser);

        Role role = referenceDataService.getRoleById(request.getRoleId());
        existingUser.setRole(role);

        User savedUser = userRepository.save(existingUser);

        log.debug("Service :: updateUser :: Exited");
        return userMapper.convertToResponse(savedUser);
    }

    @Override
    public UserResponse getUser(Long id) {
        log.debug("Service :: getUser :: Entered");

        User user = userRepository.findById(id).orElseThrow(() -> new HurecomException("User not found."));

        log.debug("Service :: getUser :: Exited");
        return userMapper.convertToResponse(user);
    }

    @Override
    public List<UserResponse> getActiveUsers() {
        log.debug("Service :: getActiveUsers :: Entered");

        Organization organization = SecurityUtils.getCurrentOrganization();
        List<UserResponse> users = userRepository.findByOrganization_IdAndActiveTrue(organization.getId()).stream()
                .map(userMapper::convertToResponse).toList();

        log.debug("Service :: getActiveUsers :: Exited");
        return users;
    }

    @Override
    public PageResponse<UserResponse> searchUsers(UserSearchRequest request) {
        log.debug("Service :: searchUsers :: Entered");

        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getLimit());

        Specification<User> spec = Specification
                .where(UserSpecification.nameContains(request.getName()))
                .and(UserSpecification.emailContains(request.getEmail()))
                .and(UserSpecification.mobileNumberContains(request.getMobileNumber()))
                .and(UserSpecification.organizationEquals(request.getOrganizationId()))
                .and(UserSpecification.roleEquals(request.getRoleId()))
                .and(UserSpecification.isActive(request.getActive()));

        Page<User> userPage = userRepository.findAll(spec, pageable);

        log.debug("Service :: searchUsers :: Exited");
        return PageUtils.convertToPageResponse(userPage, userMapper::convertToResponse);
    }

    @Override
    public List<UserResponse> getAvailableUsers(Long teamId) {
        log.debug("Service :: getAvailableUsers :: Entered");

        List<UserResponse> users = userRepository.findUsersNotInTeam(teamId).stream()
                .map(userMapper::convertToResponse).toList();

        log.debug("Service :: getAvailableUsers :: Exited");
        return users;
    }
}
