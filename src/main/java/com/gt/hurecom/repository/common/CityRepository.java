package com.gt.hurecom.repository.common;

import com.gt.hurecom.entity.common.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

    boolean existsByIdAndState_IdAndState_Country_Id(Long cityId, Long stateId, Long countryId);

    List<City> findByState_Id(Long stateId);

}
