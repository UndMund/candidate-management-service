package com.zavadskiy.candidate_management_service.service.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BaseResponse {
    String message;
}
