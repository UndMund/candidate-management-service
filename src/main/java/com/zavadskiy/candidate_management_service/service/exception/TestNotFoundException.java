package com.zavadskiy.candidate_management_service.service.exception;

import org.springframework.http.HttpStatus;

public class TestNotFoundException extends BaseException {
    public TestNotFoundException(Long id) {
        super(ApiError.builder()
                        .errorCode("test.not.found")
                        .description("Test not found with id = " + id.toString())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
