package com.zavadskiy.candidate_management_service.service.dto.test;

import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.UniqueTestName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class CreateTestDtoRequest {
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 5, max = 20, message = "Name must be 2-20 characters long")
    @UniqueTestName
    String name;
    @NotEmpty(message = "Description must not be empty")
    @Size(min = 10, max = 255, message = "Description must be 10-255 characters long")
    String description;
    @NotNull(message = "Speciality must not be empty")
    Long speciality;
}
