package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.Application;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@NullMarked
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    boolean existsByJob_IdAndCandidate_Id(Long jobId, Long candidateId);

    boolean existsByJob_IdAndCandidate_IdAndIdNot(Long jobId, Long candidateId, Long id);

    Page<Application> findAll(Specification<Application> spec, Pageable pageable);

}
