package com.zavadskiy.candidate_management_service.service.dto.file;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileDataDtoResponse {
    String id;
    String name;
    String contentType;
    Long size;
    byte[] data;
}
