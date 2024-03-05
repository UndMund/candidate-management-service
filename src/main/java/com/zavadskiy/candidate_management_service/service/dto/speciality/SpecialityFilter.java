package com.zavadskiy.candidate_management_service.service.dto.speciality;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class SpecialityFilter {
    String name;

    @JsonCreator
    public SpecialityFilter(@JsonProperty("name") String name) {
        this.name = name;
    }
}
