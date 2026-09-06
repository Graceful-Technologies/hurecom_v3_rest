package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.entity.common.MasterData;
import com.gt.hurecom.repository.common.MasterDataRepository;
import com.gt.hurecom.service.common.CacheService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CacheServiceImpl implements CacheService {

    @Autowired
    private MasterDataRepository repository;

    private final Map<String, Map<String, String>> cache = new HashMap<>();

    @PostConstruct
    public void load() {

        List<MasterData> masterDataList = repository.findAll();

        cache.clear();

        for (MasterData masterData : masterDataList) {

            cache.computeIfAbsent(
                    masterData.getType(),
                    k -> new HashMap<>());

            cache.get(masterData.getType())
                    .put(masterData.getCode(), masterData.getName());
        }
    }

    public String getName(String type, String code) {
        return cache.getOrDefault(type, Map.of())
                .getOrDefault(code, code);
    }
}
