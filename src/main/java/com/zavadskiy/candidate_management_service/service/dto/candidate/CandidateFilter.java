package com.zavadskiy.candidate_management_service.service.dto.candidate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateFilter {
    String lastname;
    String specialityName;

    @JsonCreator
    public CandidateFilter(@JsonProperty("lastname") String lastname, @JsonProperty("specialityName") String specialityName) {
        this.lastname = lastname;
        this.specialityName = specialityName;
    }
}
