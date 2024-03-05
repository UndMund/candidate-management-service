package com.zavadskiy.candidate_management_service.service.dto.candidate;

import com.zavadskiy.candidate_management_service.service.dto.file.FileDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import lombok.Builder;
import lombok.Value;

import java.util.Set;

@Value
@Builder
public class CandidateDtoResponse {
    Long id;
    String firstname;
    String lastname;
    String patronymic;
    String description;
    FileDtoResponse photo;
    FileDtoResponse cv;
    Set<SpecialityDtoResponse> specialities;
}
