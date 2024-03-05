package com.zavadskiy.candidate_management_service.service.validation.validators;

import com.zavadskiy.candidate_management_service.service.validation.validators.annotation.NotEmptyMultipart;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

public class MultipartValidator
        implements ConstraintValidator<NotEmptyMultipart, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return !multipartFile.isEmpty();
    }
}
