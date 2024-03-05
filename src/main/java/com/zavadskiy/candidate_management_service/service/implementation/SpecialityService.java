package com.zavadskiy.candidate_management_service.service.implementation;

import com.zavadskiy.candidate_management_service.database.entity.QSpeciality;
import com.zavadskiy.candidate_management_service.database.predicates.QPredicates;
import com.zavadskiy.candidate_management_service.database.repository.SpecialityRepository;
import com.zavadskiy.candidate_management_service.service.dto.PageResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.CreateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityFilter;
import com.zavadskiy.candidate_management_service.service.dto.speciality.UpdateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.exception.SpecialityNotFoundException;
import com.zavadskiy.candidate_management_service.service.interfaces.ISpecialityService;
import com.zavadskiy.candidate_management_service.service.interfaces.ISpecialityValidationService;
import com.zavadskiy.candidate_management_service.service.mappers.SpecialityMapper;
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
public class SpecialityService implements ISpecialityService, ISpecialityValidationService {
    private final SpecialityRepository specialityRepository;
    private final SpecialityMapper specialityMapper;


    @Override
    @Transactional
    public SpecialityDtoResponse create(CreateSpecialityDtoRequest request) {
        log.info("Try to create new speciality with data = {}", request.toString());

        var savedSpeciality = specialityRepository.save(
                specialityMapper.toEntity(request)
        );

        log.info("Successfully saved speciality, id = {}", savedSpeciality.getId());

        return specialityMapper.toDto(savedSpeciality);
    }

    @Override
    @Transactional
    public SpecialityDtoResponse update(Long id, UpdateSpecialityDtoRequest request) {
        log.info("Try to update speciality (id = {}) with data = {}", id, request.toString());

        var updatedSpeciality = specialityRepository.findById(id)
                .map(speciality -> {
                    specialityMapper.updateEntityFromDto(request, speciality);
                    return speciality;
                }).orElseThrow(() -> {
                    log.info("Speciality with id = {} not found", id);
                    throw new SpecialityNotFoundException(id);
                });

        log.info("Successfully updated speciality, id = {}", updatedSpeciality.getId());

        return specialityMapper.toDto(updatedSpeciality);
    }

    @Override
    public List<SpecialityDtoResponse> getAll() {
        log.info("Try to get all specialities");

        var specialities = specialityRepository.findAll()
                .stream()
                .map(specialityMapper::toDto)
                .toList();

        log.info("Successfully got all specialities, list size = {}", specialities.size());

        return specialities;
    }

    @Override
    public PageResponse<SpecialityDtoResponse> getAllByPageAndFilter(int page, int pageSize, SpecialityFilter filter) {
        log.info("Try to get all specialities on page = {}, with page size = {}; filtered by name = {}", page, pageSize, filter.getName());

        var predicate = QPredicates.builder()
                .add(filter.getName(), QSpeciality.speciality.name::eq)
                .build();
        var result = new PageResponse<>(
                specialityRepository.findAll(predicate, PageRequest.of(page, pageSize))
                        .map(specialityMapper::toDto)
        );

        log.info("Successfully got all specialities by page and filter, list size = {}", result.getContent().size());

        return result;
    }

    @Override
    public boolean isValidName(String name) {
        log.info("Try to validate speciality name = {}", name);

        var result = specialityRepository.findByName(name)
                .isEmpty();

        log.info("Successfully validated speciality name = {}, isValid = {}", name, result);

        return result;
    }

}
