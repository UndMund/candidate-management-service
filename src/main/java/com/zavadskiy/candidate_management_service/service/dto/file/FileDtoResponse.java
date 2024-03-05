package com.zavadskiy.candidate_management_service.service.dto.file;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FileDtoResponse {
    String id;
    String name;
    Long size;
    String url;
    String contentType;

    public static String getUrl(String id) {
        return "http://localhost:8080/api/v1/files/" + id;
    }
}
