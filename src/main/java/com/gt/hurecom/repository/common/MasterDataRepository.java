package com.gt.hurecom.repository.common;

import com.gt.hurecom.entity.common.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Long> {

    List<MasterData> findByTypeAndActiveTrue(String type);
}
