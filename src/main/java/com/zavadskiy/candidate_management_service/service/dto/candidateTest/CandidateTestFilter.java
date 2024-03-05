package com.zavadskiy.candidate_management_service.service.dto.candidateTest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.SortingValue;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CandidateTestFilter {
    String candidateLastname;
    String testName;
    Integer grade;
    @SortingValue
    String gradeSort;
    @SortingValue
    String dateSort;

    @JsonCreator
    public CandidateTestFilter(@JsonProperty("candidateLastname") String candidateLastname,
                               @JsonProperty("testName") String testName,
                               @JsonProperty("grade") Integer grade,
                               @JsonProperty("gradeSort") String gradeSort,
                               @JsonProperty("dateSort") String dateSort) {
        this.candidateLastname = candidateLastname;
        this.testName = testName;
        this.grade = grade;
        this.gradeSort = gradeSort;
        this.dateSort = dateSort;
    }
}
