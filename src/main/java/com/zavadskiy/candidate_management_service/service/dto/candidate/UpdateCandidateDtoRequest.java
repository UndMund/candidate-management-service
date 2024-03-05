package com.zavadskiy.candidate_management_service.service.dto.candidate;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Value
@Builder
@ToString
public class UpdateCandidateDtoRequest {
    @Size(min = 5, max = 20, message = "Firstname must be 2-20 characters long")
    String firstname;
    @Size(min = 5, max = 20, message = "Lastname must be 2-20 characters long")
    String lastname;
    @Size(min = 5, max = 20, message = "Patronymic must be 5-20 characters long")
    String patronymic;
    @Size(min = 10, max = 255, message = "Description must be 10-255 characters long")
    String description;
    MultipartFile photo;
    MultipartFile cv;
    Set<Long> specialities;
}
