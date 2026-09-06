package com.gt.hurecom.repository.recruitment;

import com.gt.hurecom.entity.recruitment.JobAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobAttachmentRepository extends JpaRepository<JobAttachment, Long> {

    boolean existsByJob_IdAndFileNameIgnoreCase(Long jobId, String fileName);

    List<JobAttachment> findByJob_Id(Long jobId);
}
