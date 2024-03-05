package com.zavadskiy.candidate_management_service.service.validation.validators.annotation;

import com.zavadskiy.candidate_management_service.service.validation.validators.MultipartValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MultipartValidator.class)
public @interface NotEmptyMultipart {
    String message() default "{candidate.file.empty}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
