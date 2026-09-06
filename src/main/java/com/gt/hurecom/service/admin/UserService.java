package com.gt.hurecom.service.admin;

import com.gt.hurecom.dto.admin.UserRequest;
import com.gt.hurecom.dto.admin.UserResponse;
import com.gt.hurecom.dto.admin.UserSearchRequest;
import com.gt.hurecom.dto.common.PageResponse;
import com.gt.hurecom.exception.HurecomException;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request) throws HurecomException;

    UserResponse updateUser(Long id, UserRequest request) throws HurecomException;

    UserResponse getUser(Long id);

    List<UserResponse> getActiveUsers();

    PageResponse<UserResponse> searchUsers(UserSearchRequest request);

    List<UserResponse> getAvailableUsers(Long teamId);

}
