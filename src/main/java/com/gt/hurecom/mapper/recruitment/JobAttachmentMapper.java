package com.gt.hurecom.mapper.recruitment;

import com.gt.hurecom.dto.recruitment.JobAttachmentResponse;
import com.gt.hurecom.entity.recruitment.JobAttachment;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobAttachmentMapper {

    @InheritConfiguration(name = "toBase")
    JobAttachmentResponse convertToResponse(JobAttachment jobAttachment);
}
