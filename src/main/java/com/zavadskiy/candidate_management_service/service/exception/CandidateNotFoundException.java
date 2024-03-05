package com.zavadskiy.candidate_management_service.service.exception;

import org.springframework.http.HttpStatus;

public class CandidateNotFoundException extends BaseException {
    public CandidateNotFoundException(Long id) {
        super(ApiError.builder()
                        .errorCode("candidate.not.found")
                        .description("Candidate not found with id = " + id.toString())
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
