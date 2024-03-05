package com.zavadskiy.candidate_management_service.service.dto.candidateTest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
@ToString
public class CreateCandidateTestDtoRequest {
    @NotNull(message = "Test must not be empty")
    Long testId;
    @NotNull(message = "Candidate must not be empty")
    Long candidateId;
    @NotNull(message = "Grade must not be empty")
    @Positive(message = "Grade must be positive")
    @Max(value = 10, message = "Grade must be less than 10")
    Integer grade;
    @NotNull(message = "Complete date must not be empty")
    @PastOrPresent(message = "Date must be past or present")
    LocalDate passedAt;
}
