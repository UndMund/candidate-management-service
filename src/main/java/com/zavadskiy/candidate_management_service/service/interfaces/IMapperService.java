package com.zavadskiy.candidate_management_service.service.interfaces;

import com.zavadskiy.candidate_management_service.database.entity.Candidate;
import com.zavadskiy.candidate_management_service.database.entity.Speciality;
import com.zavadskiy.candidate_management_service.database.entity.Test;

public interface IMapperService {
    Speciality findSpecialityById(Long id);

    Test findTestById(Long id);

    Candidate findCandidateById(Long id);

}
