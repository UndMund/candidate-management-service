package com.zavadskiy.candidate_management_service.service.mappers;

import com.zavadskiy.candidate_management_service.database.entity.Test;
import com.zavadskiy.candidate_management_service.service.dto.test.CreateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.test.TestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.test.UpdateTestDtoRequest;
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
        uses = SpecialityMapper.class
)
public abstract class TestMapper {
    @Autowired
    private IMapperService mapperService;

    public abstract Test toEntity(CreateTestDtoRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void updateEntityFromDto(UpdateTestDtoRequest dto, @MappingTarget Test entity);

    public abstract TestDtoResponse toDto(Test entity);

    Test mapId(Long id) {
        return mapperService.findTestById(id);
    }
}
