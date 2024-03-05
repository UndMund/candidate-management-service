package com.zavadskiy.candidate_management_service.service.validation.validators;

import com.zavadskiy.candidate_management_service.service.dto.SortingEnum;
import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.SortingValue;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SortingValueValidator implements ConstraintValidator<SortingValue, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return (s == null || s.isEmpty()) || SortingEnum.find(s).isPresent();
    }
}
