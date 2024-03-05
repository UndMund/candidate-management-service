package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.CreateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.test.TestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.TestFilter;
import com.zavadskiy.candidate_management_service.service.dto.test.UpdateTestDtoRequest;

import java.util.List;

public interface ITestService {
    TestDtoResponse create(CreateTestDtoRequest request);

    TestDtoResponse update(Long id, UpdateTestDtoRequest request);

    List<TestDtoResponse> getAll();

    PageResponse<TestDtoResponse> getAllByPageAndFilter(int page, int pageSize, TestFilter filter);
}
