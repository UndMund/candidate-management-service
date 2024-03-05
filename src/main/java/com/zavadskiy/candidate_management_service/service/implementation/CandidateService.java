package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.entity.QCandidate;
import com.zavadskiy.candidate_management_service.database.predicates.QPredicates;
import com.zavadskiy.candidate_management_service.database.repository.CandidateRepository;
import com.zavadskiy.candidate_management_service.database.repository.SpecialityRepository;
import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateFilter;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CreateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidate.UpdateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.CandidateNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.ICandidateService;
import com.zavadskiy.candidate_management_service.service.mappers.CandidateMapper;
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
public class CandidateService implements ICandidateService {
    private final CandidateRepository candidateRepository;
    private final SpecialityRepository specialityRepository;
    private final CandidateMapper candidateMapper;

    @Override
    @Transactional
    public CandidateDtoResponse create(CreateCandidateDtoRequest request) {
        log.info("Try to create new candidate with data = {}", request.toString());

        var savedCandidate = candidateRepository.save(
                candidateMapper.toEntity(request)
        );

        log.info("Successfully saved candidate, id = {}", savedCandidate.getId());

        return candidateMapper.toDto(savedCandidate);
    }

    @Override
    @Transactional
    public CandidateDtoResponse update(Long id, UpdateCandidateDtoRequest request) {
        log.info("Try to update candidate (id = {}) with data = {}", id, request.toString());

        var updatedCandidate = candidateRepository.findById(id)
                .map(candidate -> {
                    candidateMapper.updateEntityFromDto(request, candidate);
                    return candidate;
                }).orElseThrow(() -> {
                    log.info("Candidate with id = {} not found", id);
                    throw new CandidateNotFoundException(id);
                });

        log.info("Successfully updated candidate, id = {}", updatedCandidate.getId());

        return candidateMapper.toDto(updatedCandidate);
    }

    @Override
    public List<CandidateDtoResponse> getAll() {
        log.info("Try to get all candidates");

        var candidates = candidateRepository.findAll()
                .stream()
                .map(candidateMapper::toDto)
                .toList();

        log.info("Successfully got all candidates, list size = {}", candidates.size());

        return candidates;
    }

    @Override
    public PageResponse<CandidateDtoResponse> getAllByPageAndFilter(int page, int pageSize, CandidateFilter filter) {
        log.info("Try to get all candidates on page = {}, with page size = {}; filtered by lastname = {}, speciality name = {}", page, pageSize, filter.getLastname(), filter.getSpecialityName());

        var speciality = specialityRepository.findByName(filter.getSpecialityName()).orElse(null);

        var predicate = QPredicates.builder()
                .add(filter.getLastname(), QCandidate.candidate.lastname::eq)
                .add(speciality, QCandidate.candidate.specialities::contains)
                .build();
        var result = new PageResponse<>(
                candidateRepository.findAll(predicate, PageRequest.of(page, pageSize))
                        .map(candidateMapper::toDto)
        );

        log.info("Successfully got all candidates by page and filter, list size = {}", result.getContent().size());

        return result;
    }
}
