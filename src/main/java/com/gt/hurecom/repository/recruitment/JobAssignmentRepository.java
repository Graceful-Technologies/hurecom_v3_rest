package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.JobAssignment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobAssignmentRepository extends JpaRepository<JobAssignment, Long> {

    boolean existsByJob_IdAndTeam_Id(Long jobId, Long teamId);

    Optional<JobAssignment> findByJob_IdAndTeam_Id(Long jobId, Long teamId);

    @EntityGraph(attributePaths = {"team"})
    List<JobAssignment> findByJob_IdAndEndDateIsNull(Long jobId);
}
