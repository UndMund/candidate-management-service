package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.CreateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityFilter;
import com.zavadskiy.candidate_management_service.service.dto.speciality.UpdateSpecialityDtoRequest;

import java.util.List;

public interface ISpecialityService {
    List<SpecialityDtoResponse> getAll();

    PageResponse<SpecialityDtoResponse> getAllByPageAndFilter(int page, int pageSize, SpecialityFilter filter);

    SpecialityDtoResponse create(CreateSpecialityDtoRequest request);

    SpecialityDtoResponse update(Long id, UpdateSpecialityDtoRequest request);

}
