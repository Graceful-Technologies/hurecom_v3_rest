package com.gt.hurecom.service.common;

import com.gt.hurecom.dto.common.MasterDataResponse;

import java.util.List;

public interface MasterDataService {

    List<MasterDataResponse> getDataByType(String type);
}
