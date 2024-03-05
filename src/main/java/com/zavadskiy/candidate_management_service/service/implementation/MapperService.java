package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.entity.Candidate;
import com.zavadskiy.candidate_management_service.database.entity.Speciality;
import com.zavadskiy.candidate_management_service.database.entity.Test;
import com.zavadskiy.candidate_management_service.database.repository.CandidateRepository;
import com.zavadskiy.candidate_management_service.database.repository.SpecialityRepository;
import com.zavadskiy.candidate_management_service.database.repository.TestRepository;
import com.zavadskiy.candidate_management_service.service.exception.CandidateNotFoundException;
import com.zavadskiy.candidate_management_service.service.exception.SpecialityNotFoundException;
import com.zavadskiy.candidate_management_service.service.exception.TestNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.IMapperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapperService implements IMapperService {
    private final CandidateRepository candidateRepository;
    private final SpecialityRepository specialityRepository;
    private final TestRepository testRepository;

    @Override
    public Candidate findCandidateById(Long id) {
        log.info("Try to find candidate by id = {}", id);

        var candidate = candidateRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Candidate with id = {} not found", id);
                    throw new CandidateNotFoundException(id);
                });

        log.info("Successfully found candidate with id = {}", id);

        return candidate;
    }

    @Override
    public Speciality findSpecialityById(Long id) {
        log.info("Try to find speciality by id = {}", id);

        var speciality = specialityRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Speciality with id = {} not found", id);
                    throw new SpecialityNotFoundException(id);
                });

        log.info("Successfully found speciality with id = {}", id);

        return speciality;
    }

    @Override
    public Test findTestById(Long id) {
        log.info("Try to find test by id = {}", id);

        var test = testRepository.findById(id)
                .orElseThrow(() -> {
                    log.info("Test with id = {} not found", id);
                    throw new TestNotFoundException(id);
                });

        log.info("Successfully found test with id = {}", id);

        return test;
    }
}
