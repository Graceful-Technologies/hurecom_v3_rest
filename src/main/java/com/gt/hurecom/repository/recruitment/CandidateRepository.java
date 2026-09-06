package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.Candidate;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long>, CandidateRepositoryCustom {

    boolean existsByEmailIgnoreCaseAndOrganization_Id(String email, Long organizationId);

    boolean existsByMobileNumberAndOrganization_Id(String mobileNumber, Long organizationId);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    boolean existsByMobileNumberAndIdNot(String mobileNumber, Long id);

}
