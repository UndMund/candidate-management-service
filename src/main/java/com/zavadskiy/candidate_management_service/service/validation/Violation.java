package com.zavadskiy.candidate_management_service.service.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Violation {
    private final String fieldName;
    private final String message;
}
