package com.zavadskiy.candidate_management_service.service.dto.candidate;

import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.NotEmptyMultipart;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@Value
@Builder
@ToString
public class CreateCandidateDtoRequest {
    @NotEmpty(message = "Firstname must not be empty")
    @Size(min = 5, max = 20, message = "Firstname must be 2-20 characters long")
    String firstname;
    @NotEmpty(message = "Lastname must not be empty")
    @Size(min = 5, max = 20, message = "Lastname must be 2-20 characters long")
    String lastname;
    @NotEmpty(message = "Patronymic must not be empty")
    @Size(min = 5, max = 20, message = "Patronymic must be 5-20 characters long")
    String patronymic;
    @NotEmpty(message = "Description must not be empty")
    @Size(min = 10, max = 255, message = "Description must be 10-255 characters long")
    String description;
    @NotEmptyMultipart
    MultipartFile photo;
    @NotEmptyMultipart
    MultipartFile cv;
    @NotEmpty(message = "Specialities must not be empty")
    Set<Long> specialities;
}
