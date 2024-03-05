package com.zavadskiy.candidate_management_service.service.dto;

import java.util.Arrays;
import java.util.Optional;

public enum SortingEnum {
    ASC, DESC;

    public static Optional<SortingEnum> find(String sortingValue) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(sortingValue))
                .findFirst();
    }
}
