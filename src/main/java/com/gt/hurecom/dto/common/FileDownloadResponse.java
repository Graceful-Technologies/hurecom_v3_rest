package com.gt.hurecom.dto.common;

import org.springframework.core.io.Resource;

public class FileDownloadResponse {

    private Resource resource;

    private String fileName;

    private String fileType;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
