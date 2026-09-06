package com.gt.hurecom.service.impl.common;

import com.gt.hurecom.dto.common.FileUploadResponse;
import com.gt.hurecom.enums.FileCategory;
import com.gt.hurecom.exception.HurecomException;
import com.gt.hurecom.service.common.FileService;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private static final Logger log = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path uploadBase;

    @PostConstruct
    public void init() {
        this.uploadBase = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    @Override
    public FileUploadResponse upload(MultipartFile file, FileCategory category, Long referenceId) {
        log.debug("Service :: upload :: Entered");

        if (Objects.isNull(file) || file.isEmpty()) {
            throw new HurecomException("File is empty.");
        }

        // check file size is below 5MB
        if (file.getSize() > 5_000_000) {
            throw new HurecomException("File size exceeds limit.");
        }

        if (Objects.isNull(referenceId)) {
            throw new HurecomException("Reference ID is required.");
        }

        String contentType = file.getContentType();
        String extension = getExtension(contentType);

        String storedFileName = UUID.randomUUID() + extension;
        FileUploadResponse response;

        try {
            Path uploadPath = resolvePath(category, referenceId);
            Files.createDirectories(uploadPath);

            Path targetLocation = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Path relativePath = uploadBase.relativize(targetLocation);

            response = new FileUploadResponse();
            response.setFileName(file.getOriginalFilename());
            response.setFilePath(relativePath.toString());
            response.setFileType(contentType);
            response.setFileSize(file.getSize());

        } catch (IOException e) {
            log.error("Service :: upload :: Exception :: {}", e.getMessage());
            throw new HurecomException("File upload failed.");
        }
        log.debug("Service :: upload :: Exited");
        return response;
    }

    @Override
    public Resource download(String filePath) {
        log.debug("Service :: download :: Entered");
        Resource resource;
        try {
            Path path = uploadBase.resolve(filePath).normalize();
            resource = new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new HurecomException("File not found");
            }

        } catch (MalformedURLException e) {
            log.error("Service :: download :: Exception :: {}", e.getMessage());
            throw new HurecomException("Invalid file path.");
        }
        log.debug("Service :: download :: Exited");
        return resource;
    }

    @Override
    public void delete(String filePath) {
        try {
            Path path = uploadBase.resolve(filePath).normalize();
            Files.deleteIfExists(path);
        } catch (IOException e) {
            log.error("Service :: delete :: Exception :: {}", e.getMessage());
            throw new HurecomException("File delete failed.");
        }
    }

    private Path resolvePath(FileCategory category, Long referenceId) {
        return switch (category) {
            case JOB_ATTACHMENT -> uploadBase.resolve("job-attachments/job-" + referenceId);
            case RESUME -> uploadBase.resolve("resumes/candidate-" + referenceId);
            case PROFILE_PHOTO -> uploadBase.resolve("profile-photos/user-" + referenceId);
        };
    }

    private String getExtension(String contentType) {
        if (contentType == null) return ".bin";

        return switch (contentType) {
            case "text/plain" -> ".txt";
            case "application/pdf" -> ".pdf";
            case "application/msword" -> ".doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> ".docx";
            case "application/vnd.ms-excel" -> ".xls";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" -> ".xlsx";
            case "image/jpeg" -> ".jpg";
            case "image/png" -> ".png";
            default -> ".bin";
        };
    }
}
