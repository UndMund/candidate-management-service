package com.zavadskiy.candidate_management_service.service.dto.candidateTest;

import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.TestDtoResponse;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class CandidateTestDtoResponse {
    Long id;
    TestDtoResponse test;
    CandidateDtoResponse candidate;
    Integer grade;
    LocalDate passedAt;
}
