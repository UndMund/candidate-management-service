package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.entity.QCandidateTest;
import com.zavadskiy.candidate_management_service.database.predicates.QPredicates;
import com.zavadskiy.candidate_management_service.database.repository.CandidateTestRepository;
import com.zavadskiy.candidate_management_service.service.dto.FilterResolver;
import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CreateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.UpdateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.CandidateTestNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.ICandidateTestService;
import com.zavadskiy.candidate_management_service.service.mappers.CandidateTestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandidateTestService implements ICandidateTestService {
    private final CandidateTestRepository candidateTestRepository;
    private final CandidateTestMapper candidateTestMapper;

    @Override
    public CandidateTestDtoResponse create(CreateCandidateTestDtoRequest request) {
        log.info("Try to create new candidate's test with data = {}", request.toString());

        var savedCandidateTest = candidateTestRepository.save(
                candidateTestMapper.toEntity(request)
        );

        log.info("Successfully saved candidate's test, id = {}", savedCandidateTest.getId());

        return candidateTestMapper.toDto(savedCandidateTest);
    }

    @Override
    public CandidateTestDtoResponse update(Long id, UpdateCandidateTestDtoRequest request) {
        log.info("Try to update candidate's test (id = {}) with data = {}", id, request.toString());

        var updatedCandidateTest = candidateTestRepository.findById(id)
                .map(candidateTest -> {
                    candidateTestMapper.updateEntityFromDto(request, candidateTest);
                    return candidateTest;
                }).orElseThrow(() -> {
                    log.info("Candidate's test with id = {} not found", id);
                    throw new CandidateTestNotFoundException(id);
                });

        log.info("Successfully updated candidate's test, id = {}", updatedCandidateTest.getId());

        return candidateTestMapper.toDto(updatedCandidateTest);
    }

    @Override
    public List<CandidateTestDtoResponse> getAll() {
        log.info("Try to get all candidate tests");

        var candidateTests = candidateTestRepository.findAll()
                .stream()
                .map(candidateTestMapper::toDto)
                .toList();

        log.info("Successfully got all candidate tests, list size = {}", candidateTests.size());

        return candidateTests;
    }

    @Override
    public PageResponse<CandidateTestDtoResponse> getAllByPageAndFilter(int page, int pageSize, CandidateTestFilter filter) {
        log.info("Try to get all candidates on page = {}, with page size = {}; filtered by candidate lastname = {}, test name = {}, grade = {}; sorted by grade {}, date {}",
                page, pageSize, filter.getCandidateLastname(), filter.getTestName(), filter.getGrade(), filter.getGradeSort(), filter.getDateSort());

        var predicate = QPredicates.builder()
                .add(filter.getCandidateLastname(), QCandidateTest.candidateTest.candidate.lastname::eq)
                .add(filter.getTestName(), QCandidateTest.candidateTest.test.name::eq)
                .add(filter.getGrade(), QCandidateTest.candidateTest.grade::eq)
                .build();
        var result = new PageResponse<>(
                candidateTestRepository.findAll(
                        predicate,
                        FilterResolver.handleCandidateTestSort(page, pageSize, filter)
                ).map(candidateTestMapper::toDto)
        );

        log.info("Successfully got all candidate tests by page and filter, list size = {}", result.getContent().size());

        return result;
    }
}
