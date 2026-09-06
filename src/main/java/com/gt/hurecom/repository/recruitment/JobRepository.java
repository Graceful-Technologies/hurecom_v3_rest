package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.Job;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
@NullMarked
public interface JobRepository extends JpaRepository<Job, Long>, JobRepositoryCustom {

    boolean existsByClient_IdAndClientLocation_IdAndTitleIgnoreCase(Long clientId, Long clientLocationId, String title);

    boolean existsByClient_IdAndClientLocation_IdAndTitleIgnoreCaseAndIdNot(Long clientId, Long clientLocationId, String title, Long id);

    @EntityGraph(attributePaths = {
            "client",
            "clientLocation",
            "clientLocation.city",
            "clientSpoc"
    })
    Page<Job> findAll(Specification<Job> spec, Pageable pageable);
}
