package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CreateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.UpdateCandidateTestDtoRequest;

import java.util.List;

public interface ICandidateTestService {
    CandidateTestDtoResponse create(CreateCandidateTestDtoRequest request);

    CandidateTestDtoResponse update(Long id, UpdateCandidateTestDtoRequest request);

    List<CandidateTestDtoResponse> getAll();

    PageResponse<CandidateTestDtoResponse> getAllByPageAndFilter(int page, int pageSize, CandidateTestFilter filter);
}
