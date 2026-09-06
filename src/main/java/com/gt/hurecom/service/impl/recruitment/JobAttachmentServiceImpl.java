package com.gt.hurecom.service.impl.recruitment;

import com.gt.hurecom.dto.common.FileDownloadResponse;
import com.gt.hurecom.dto.common.FileUploadResponse;
import com.gt.hurecom.dto.recruitment.JobAttachmentRequest;
import com.gt.hurecom.dto.recruitment.JobAttachmentResponse;
import com.gt.hurecom.entity.recruitment.Job;
import com.gt.hurecom.entity.recruitment.JobAttachment;
import com.gt.hurecom.enums.FileCategory;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.mapper.recruitment.JobAttachmentMapper;
import com.gt.hurecom.repository.recruitment.JobAttachmentRepository;
import com.gt.hurecom.service.common.FileService;
import com.gt.hurecom.service.common.ReferenceDataService;
import com.gt.hurecom.service.recruitment.JobAttachmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class JobAttachmentServiceImpl implements JobAttachmentService {

    private static final Logger log = LoggerFactory.getLogger(JobAttachmentServiceImpl.class);

    private final JobAttachmentRepository jobAttachmentRepository;

    private final JobAttachmentMapper jobAttachmentMapper;

    private final ReferenceDataService referenceDataService;

    private final FileService fileService;

    public JobAttachmentServiceImpl(JobAttachmentRepository jobAttachmentRepository,
                                    JobAttachmentMapper jobAttachmentMapper,
                                    ReferenceDataService referenceDataService,
                                    FileService fileService) {
        super();
        this.jobAttachmentRepository = jobAttachmentRepository;
        this.jobAttachmentMapper = jobAttachmentMapper;
        this.referenceDataService = referenceDataService;
        this.fileService = fileService;
    }

    @Override
    public void uploadAttachment(JobAttachmentRequest request) {
        log.debug("Service :: uploadAttachment :: Entered");

        Job job = referenceDataService.getJobById(request.getJobId());

        for (MultipartFile file : request.getFiles()) {
            FileUploadResponse uploadResponse = fileService.upload(file, FileCategory.JOB_ATTACHMENT, request.getJobId());
            JobAttachment attachment = new JobAttachment();
            attachment.setFileName(uploadResponse.getFileName());
            attachment.setFileType(uploadResponse.getFileType());
            attachment.setFilePath(uploadResponse.getFilePath());
            attachment.setFileSize(uploadResponse.getFileSize());
            attachment.setJob(job);
            jobAttachmentRepository.save(attachment);
        }
        log.debug("Service :: uploadAttachment :: Exited");
    }

    @Override
    public void removeAttachment(Long id) {
        log.debug("Service :: removeAttachment :: Entered");

        JobAttachment attachment = jobAttachmentRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Attachment not found."));

        fileService.delete(attachment.getFilePath());
        jobAttachmentRepository.delete(attachment);

        log.debug("Service :: removeAttachment :: Exited");
    }

    @Override
    public FileDownloadResponse downloadAttachment(Long id) {
        log.debug("Service :: downloadAttachment :: Entered");

        JobAttachment attachment = jobAttachmentRepository.findById(id)
                .orElseThrow(() -> new HurecomException("Attachment not found."));

        Resource resource = fileService.download(attachment.getFilePath());

        FileDownloadResponse response = new FileDownloadResponse();
        response.setResource(resource);
        response.setFileName(attachment.getFileName());
        response.setFileType(attachment.getFileType());

        log.debug("Service :: downloadAttachment :: Exited");
        return response;
    }

    @Override
    public List<JobAttachmentResponse> getAttachments(Long jobId) {
        log.debug("Service :: getAttachments :: Entered");

        List<JobAttachmentResponse> attachments = jobAttachmentRepository.findByJob_Id(jobId).stream()
                .map(jobAttachmentMapper::convertToResponse).toList();

        log.debug("Service :: getAttachments :: Exited");
        return attachments;
    }

}
