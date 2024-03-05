package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.entity.QTest;
import com.zavadskiy.candidate_management_service.database.predicates.QPredicates;
import com.zavadskiy.candidate_management_service.database.repository.TestRepository;
import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.CreateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.test.TestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.TestFilter;
import com.zavadskiy.candidate_management_service.service.dto.test.UpdateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.TestNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.ITestService;
import com.zavadskiy.candidate_management_service.service.interfaces.ITestValidationService;
import com.zavadskiy.candidate_management_service.service.mappers.TestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestService implements ITestService, ITestValidationService {
    private final TestRepository testRepository;
    private final TestMapper testMapper;

    @Override
    @Transactional
    public TestDtoResponse create(CreateTestDtoRequest request) {
        log.info("Try to create new test with data = {}", request.toString());

        var savedTest = testRepository.save(
                testMapper.toEntity(request)
        );

        log.info("Successfully saved test, id = {}", savedTest.getId());

        return testMapper.toDto(savedTest);
    }

    @Override
    @Transactional
    public TestDtoResponse update(Long id, UpdateTestDtoRequest request) {
        log.info("Try to update test (id = {}) with data = {}", id, request.toString());

        var updatedTest = testRepository.findById(id)
                .map(test -> {
                    testMapper.updateEntityFromDto(request, test);
                    return test;
                }).orElseThrow(() -> {
                    log.info("Test with id = {} not found", id);
                    throw new TestNotFoundException(id);
                });

        log.info("Successfully updated test, id = {}", updatedTest.getId());

        return testMapper.toDto(updatedTest);
    }

    @Override
    public List<TestDtoResponse> getAll() {
        log.info("Try to get all tests");

        var tests = testRepository.findAll()
                .stream()
                .map(testMapper::toDto)
                .toList();

        log.info("Successfully got all tests, list size = {}", tests.size());

        return tests;
    }

    @Override
    public PageResponse<TestDtoResponse> getAllByPageAndFilter(int page, int pageSize, TestFilter filter) {
        log.info("Try to get all tests on page = {}, with page size = {}; filtered by name = {}, speciality name = {}", page, pageSize, filter.getName(), filter.getSpecialityName());

        var predicate = QPredicates.builder()
                .add(filter.getName(), QTest.test.name::eq)
                .add(filter.getSpecialityName(), QTest.test.speciality.name::eq)
                .build();
        var result = new PageResponse<>(
                testRepository.findAll(predicate, PageRequest.of(page, pageSize))
                        .map(testMapper::toDto)
        );

        log.info("Successfully got all tests by page and filter, list size = {}", result.getContent().size());

        return result;
    }

    @Override
    public boolean isValidName(String name) {
        log.info("Try to validate test name = {}", name);

        var result = testRepository.findByName(name)
                .isEmpty();

        log.info("Successfully validated test name = {}, isValid = {}", name, result);

        return result;
    }
}
