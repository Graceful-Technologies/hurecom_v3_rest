package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.JobCommission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobCommissionRepository extends JpaRepository<JobCommission, Long> {

    Optional<JobCommission> findByJob_IdAndEndDateIsNull(Long jobId);

    List<JobCommission> findByJob_Id(Long jobId);
}
