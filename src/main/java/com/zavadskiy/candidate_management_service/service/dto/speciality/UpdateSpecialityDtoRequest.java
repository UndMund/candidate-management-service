package com.zavadskiy.candidate_management_service.service.dto.speciality;

import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.UniqueSpecialityName;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@Builder
@ToString
public class UpdateSpecialityDtoRequest {
    @Size(min = 5, max = 20, message = "Name must be 2-20 characters long")
    @UniqueSpecialityName(nullable = true)
    String name;
    @Size(min = 10, max = 255, message = "Description must be 10-255 characters long")
    String description;
}
