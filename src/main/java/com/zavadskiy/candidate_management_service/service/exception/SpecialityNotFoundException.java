package com.zavadskiy.candidate_management_service.service.exception;

import org.springframework.http.HttpStatus;

public class SpecialityNotFoundException extends BaseException {
    public SpecialityNotFoundException(Long id) {
        super(ApiError.builder()
                        .errorCode("speciality.not.found")
                        .description("Speciality not found with id = " + id.toString())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
