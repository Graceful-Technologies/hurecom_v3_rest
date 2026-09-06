package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.CandidateResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidateResumeRepository extends JpaRepository<CandidateResume, Long> {

    boolean existsByCandidate_IdAndFileNameIgnoreCase(Long candidateId, String fileName);

    List<CandidateResume> findByCandidate_Id(Long candidateId);
}
