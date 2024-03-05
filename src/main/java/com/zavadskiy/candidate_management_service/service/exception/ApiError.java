package com.zavadskiy.candidate_management_service.service.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiError {
    private String errorCode;
    private String description;
}
