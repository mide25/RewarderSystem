package com.takehome.rewarder.models.api;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

public class FileUploadModel {

    @NotNull
    public MultipartFile multipartFile;

    public MultipartFile getMultipartFile() {
        return multipartFile;
    }

    public void setMultipartFile(MultipartFile multipartFile) {
        this.multipartFile = multipartFile;
    }
}
