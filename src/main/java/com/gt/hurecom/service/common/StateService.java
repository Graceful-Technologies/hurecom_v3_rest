package com.gt.hurecom.service.common;

import com.gt.hurecom.dto.common.StateResponse;

import java.util.List;

public interface StateService {

    List<StateResponse> getStates(Long countryId);
}
