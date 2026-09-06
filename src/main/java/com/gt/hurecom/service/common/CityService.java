package com.gt.hurecom.service.common;

import com.gt.hurecom.dto.common.CityResponse;

import java.util.List;

public interface CityService {

    List<CityResponse> getCities(Long stateId);
}
