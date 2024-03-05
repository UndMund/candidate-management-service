package com.zavadskiy.candidate_management_service.service.validation.validators.annotation;

import com.zavadskiy.candidate_management_service.service.validation.validators.TestNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TestNameValidator.class)
public @interface UniqueTestName {
    String message() default "{test.name.notUnique}";

    boolean nullable() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
