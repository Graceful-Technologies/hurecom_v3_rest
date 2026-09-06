package com.gt.hurecom.service.common;

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

import java.util.List;

public interface ReferenceDataService {

    Organization getOrganizationById(Long id);

    Role getRoleById(Long id);

    User getUserById(Long id);

    List<User> getUsers(List<Long> userIds);

    Client getClientById(Long id);

    ClientLocation getClientLocationById(Long id);

    ClientSpoc getClientSpocById(Long id);

    Country getCountryById(Long id);

    State getStateById(Long id);

    City getCityById(Long id);

    void validateLocationHierarchy(Long cityId, Long stateId, Long countryId);

    Job getJobById(Long id);

    Team getTeamById(Long id);

    List<Team> getTeams(List<Long> teamIds);

    Candidate getCandidateById(Long id);

}
