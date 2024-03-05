package com.zavadskiy.candidate_management_service.service.mappers;

import com.zavadskiy.candidate_management_service.database.entity.Candidate;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CandidateDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidate.CreateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidate.UpdateCandidateDtoRequest;
import com.zavadskiy.candidate_management_service.service.interfaces.IMapperService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(
        componentModel = "spring",
        uses = {SpecialityMapper.class, FileMapper.class}
)
public abstract class CandidateMapper {
    @Autowired
    private IMapperService mapperService;

    public abstract Candidate toEntity(CreateCandidateDtoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(UpdateCandidateDtoRequest dto, @MappingTarget Candidate entity);

    public abstract CandidateDtoResponse toDto(Candidate entity);

    Candidate mapId(Long id) {
        return mapperService.findCandidateById(id);
    }
}
