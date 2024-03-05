package com.zavadskiy.candidate_management_service.service.dto;

import com.zavadskiy.candidate_management_service.database.entity.CandidateTest;
import com.zavadskiy.candidate_management_service.service.dto.candidateTest.CandidateTestFilter;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class FilterResolver {
    public static Pageable handleCandidateTestSort(int page, int size, CandidateTestFilter filter) {
        Sort gradeSort = Sort.unsorted();
        Sort dateSort = Sort.unsorted();

        if (SortingEnum.ASC.name().equals(filter.getGradeSort())) {
            gradeSort = Sort.by(CandidateTest.Fields.grade).ascending();

        } else if (SortingEnum.DESC.name().equals(filter.getGradeSort())) {
            gradeSort = Sort.by(CandidateTest.Fields.grade).descending();
        }

        if (SortingEnum.ASC.name().equals(filter.getDateSort())) {
            dateSort = Sort.by(CandidateTest.Fields.passedAt).ascending();

        } else if (SortingEnum.DESC.name().equals(filter.getDateSort())) {
            dateSort = Sort.by(CandidateTest.Fields.passedAt).descending();
        }

        return PageRequest.of(page, size, gradeSort.and(dateSort));
    }
}
