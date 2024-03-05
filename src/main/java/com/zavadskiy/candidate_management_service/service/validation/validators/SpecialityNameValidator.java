package com.zavadskiy.candidate_management_service.service.validation.validators;

import com.zavadskiy.candidate_management_service.service.interfaces.ISpecialityValidationService;
import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.UniqueSpecialityName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class SpecialityNameValidator
        implements ConstraintValidator<UniqueSpecialityName, String> {
    @Autowired
    private ISpecialityValidationService validationService;
    private Boolean nullable;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (nullable) {
            return (s == null) || validationService.isValidName(s);
        } else {
            return validationService.isValidName(s);
        }
    }

    @Override
    public void initialize(UniqueSpecialityName constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }
}
