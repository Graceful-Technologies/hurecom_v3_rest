package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.entity.admin.Organization;
import com.gt.hurecom.entity.admin.Role;
import com.gt.hurecom.entity.admin.Team;
import com.gt.hurecom.entity.admin.User;
import com.gt.hurecom.entity.common.City;
import com.gt.hurecom.entity.common.Country;
import com.gt.hurecom.entity.common.State;
import com.gt.hurecom.entity.master.Client;
import com.gt.hurecom.entity.master.ClientLocation;
import com.gt.hurecom.entity.master.ClientSpoc;
import com.gt.hurecom.entity.recruitment.Candidate;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.repository.admin.OrganizationRepository;
import com.gt.hurecom.repository.admin.RoleRepository;
import com.gt.hurecom.repository.admin.TeamRepository;
import com.gt.hurecom.repository.admin.UserRepository;
import com.gt.hurecom.repository.common.CityRepository;
import com.gt.hurecom.repository.common.CountryRepository;
import com.gt.hurecom.repository.common.StateRepository;
import com.gt.hurecom.repository.master.ClientLocationRepository;
import com.gt.hurecom.repository.master.ClientRepository;
import com.gt.hurecom.repository.master.ClientSpocRepository;
import com.gt.hurecom.repository.recruitment.CandidateRepository;
import com.gt.hurecom.repository.recruitment.JobRepository;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.utility.CommonUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferenceDataServiceImpl implements ReferenceDataService {

    private final OrganizationRepository organizationRepository;

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final ClientRepository clientRepository;

    private final ClientLocationRepository clientLocationRepository;

    private final ClientSpocRepository clientSpocRepository;

    private final CountryRepository countryRepository;

    private final StateRepository stateRepository;

    private final CityRepository cityRepository;

    private final JobRepository jobRepository;

    private final TeamRepository teamRepository;

    private final CandidateRepository candidateRepository;

    public ReferenceDataServiceImpl(OrganizationRepository organizationRepository,
                                    RoleRepository roleRepository,
                                    UserRepository userRepository,
                                    ClientRepository clientRepository,
                                    ClientLocationRepository clientLocationRepository,
                                    ClientSpocRepository clientSpocRepository,
                                    CountryRepository countryRepository,
                                    StateRepository stateRepository,
                                    CityRepository cityRepository,
                                    JobRepository jobRepository,
                                    TeamRepository teamRepository,
                                    CandidateRepository candidateRepository) {
        super();
        this.organizationRepository = organizationRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.clientLocationRepository = clientLocationRepository;
        this.clientSpocRepository = clientSpocRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
        this.jobRepository = jobRepository;
        this.teamRepository = teamRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Organization not found."));
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Role not found."));
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HurecomException("User not found."));
    }

    @Override
    public List<User> getUsers(List<Long> userIds) {
        return userRepository.findByIdIn(userIds);
    }

    @Override
    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client not found."));
    }

    @Override
    public ClientLocation getClientLocationById(Long id) {
        return clientLocationRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client location not found."));
    }

    @Override
    public ClientSpoc getClientSpocById(Long id) {
        return clientSpocRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Client spoc not found."));
    }

    @Override
    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Country not found."));
    }

    @Override
    public State getStateById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new HurecomException("State not found."));
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new HurecomException("City not found."));
    }

    @Override
    public void validateLocationHierarchy(Long cityId, Long stateId, Long countryId) {
        if (!cityRepository.existsByIdAndState_IdAndState_Country_Id(cityId, stateId, countryId)) {
            CommonUtils.throwBusinessException("Invalid country, state, or city combination.");
        }
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Job not found."));
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Team not found."));
    }

    @Override
    public List<Team> getTeams(List<Long> teamIds) {
        return teamRepository.findByIdIn(teamIds);
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Candidate not found."));
    }
}
