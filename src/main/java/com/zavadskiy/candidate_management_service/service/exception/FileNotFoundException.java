package com.zavadskiy.candidate_management_service.service.exception;

import org.springframework.http.HttpStatus;

public class FileNotFoundException extends BaseException {
    public FileNotFoundException(String id) {
        super(ApiError.builder()
                        .errorCode("file.not.found")
                        .description("File not found with id = " + id)
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
