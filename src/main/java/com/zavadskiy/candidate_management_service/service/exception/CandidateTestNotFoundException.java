package com.zavadskiy.candidate_management_service.service.exception;

import org.springframework.http.HttpStatus;

public class CandidateTestNotFoundException extends BaseException {
    public CandidateTestNotFoundException(Long id) {
        super(ApiError.builder()
                        .errorCode("candidate.test.not.found")
                        .description("Candidate's test not found with id = " + id)
                        .build(),
                HttpStatus.NOT_FOUND);
    }
}
