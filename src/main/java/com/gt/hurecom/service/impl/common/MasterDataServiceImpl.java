package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.dto.common.MasterDataResponse;
import com.gt.hurecom.mapper.common.MasterDataMapper;
import com.gt.hurecom.repository.common.MasterDataRepository;
import com.gt.hurecom.service.common.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataServiceImpl implements MasterDataService {

    private static final Logger log = LoggerFactory.getLogger(MasterDataServiceImpl.class);

    private final MasterDataRepository masterDataRepository;

    private final MasterDataMapper masterDataMapper;

    public MasterDataServiceImpl(MasterDataRepository masterDataRepository,
                                 MasterDataMapper masterDataMapper) {
        super();
        this.masterDataRepository = masterDataRepository;
        this.masterDataMapper = masterDataMapper;
    }

    @Override
    public List<MasterDataResponse> getDataByType(String type) {
        log.debug("Service :: getDataByType :: Entered");

        List<MasterDataResponse> response = masterDataRepository.findByTypeAndActiveTrue(type).stream()
                .map(masterDataMapper::convertToResponse).toList();

        log.debug("Service :: getDataByType :: Exited");
        return response;
    }
}
