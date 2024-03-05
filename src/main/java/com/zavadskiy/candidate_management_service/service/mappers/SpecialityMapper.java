package com.zavadskiy.candidate_management_service.service.mappers;

import com.zavadskiy.candidate_management_service.database.entity.Speciality;
import com.zavadskiy.candidate_management_service.service.dto.speciality.CreateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.speciality.SpecialityDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.speciality.UpdateSpecialityDtoRequest;
import com.zavadskiy.candidate_management_service.service.interfaces.IMapperService;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public abstract class SpecialityMapper {
    @Autowired
    private IMapperService mapperService;

    public abstract Speciality toEntity(CreateSpecialityDtoRequest dto);

    public abstract SpecialityDtoResponse toDto(Speciality entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(UpdateSpecialityDtoRequest dto, @MappingTarget Speciality entity);

    Speciality mapId(Long id) {
        return mapperService.findSpecialityById(id);
    }
}
