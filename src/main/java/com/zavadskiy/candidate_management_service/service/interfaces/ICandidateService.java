package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CreateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidate.UpdateCandidateDtoRequest;

import java.util.List;

public interface ICandidateService {
    CandidateDtoResponse create(CreateCandidateDtoRequest request);

    CandidateDtoResponse update(Long id, UpdateCandidateDtoRequest request);

    List<CandidateDtoResponse> getAll();

    PageResponse<CandidateDtoResponse> getAllByPageAndFilter(int page, int pageSize, CandidateFilter filter);
}
