package com.gt.hurecom.service.common;

import com.gt.hurecom.dto.common.FileUploadResponse;
import com.gt.hurecom.enums.FileCategory;
import com.gt.hurecom.exception.HurecomException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileUploadResponse upload(MultipartFile file, FileCategory category, Long referenceId);

    Resource download(String filePath);

    void delete(String filePath);

}
