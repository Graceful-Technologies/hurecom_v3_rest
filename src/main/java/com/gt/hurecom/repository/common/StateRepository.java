package com.gt.hurecom.repository.common;

import com.gt.hurecom.entity.common.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StateRepository extends JpaRepository<State, Long>, JpaSpecificationExecutor<State> {

    List<State> findByCountry_Id(Long countryId);

}
