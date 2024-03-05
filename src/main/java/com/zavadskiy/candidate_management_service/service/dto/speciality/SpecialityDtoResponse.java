package com.zavadskiy.candidate_management_service.service.dto.speciality;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialityDtoResponse {
    Long id;
    String name;
    String description;
}
