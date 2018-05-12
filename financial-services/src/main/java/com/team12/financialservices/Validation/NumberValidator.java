package com.team12.financialservices.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NumberValidation.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface NumberValidator {

    String message() default "Enter a number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}