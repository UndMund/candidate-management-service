package com.zavadskiy.candidate_management_service.service.dto.test;

import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TestDtoResponse {
    Long id;
    String name;
    String description;
    SpecialityDtoResponse speciality;
}
