package com.zavadskiy.candidate_management_service.service.validation.validators;

import com.zavadskiy.candidate_management_service.service.interfaces.ITestValidationService;
import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.UniqueTestName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class TestNameValidator
        implements ConstraintValidator<UniqueTestName, String> {
    @Autowired
    private ITestValidationService validationService;
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
    public void initialize(UniqueTestName constraintAnnotation) {
        this.nullable = constraintAnnotation.nullable();
    }
}
