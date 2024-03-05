package com.zavadskiy.candidate_management_service.service.mappers;

import com.zavadskiy.candidate_management_service.database.entity.CandidateTest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestDtoResponse;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CreateCandidateTestDtoRequest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.UpdateCandidateTestDtoRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(
        componentModel = "spring",
        uses = {TestMapper.class, CandidateMapper.class}
)
public interface CandidateTestMapper {
    @Mapping(target = "test", source = "testId")
    @Mapping(target = "candidate", source = "candidateId")
    CandidateTest toEntity(CreateCandidateTestDtoRequest dto);

    @Mapping(target = "test", source = "testId")
    @Mapping(target = "candidate", source = "candidateId")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UpdateCandidateTestDtoRequest dto, @MappingTarget CandidateTest entity);

    CandidateTestDtoResponse toDto(CandidateTest candidateTest);
}
