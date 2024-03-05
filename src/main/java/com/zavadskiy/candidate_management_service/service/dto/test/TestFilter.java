package com.zavadskiy.candidate_management_service.service.dto.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TestFilter {
    String name;
    String specialityName;

    @JsonCreator
    public TestFilter(@JsonProperty("name") String name, @JsonProperty("specialityName") String specialityName) {
        this.name = name;
        this.specialityName = specialityName;
    }
}
