package com.team12.financialservices.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StringValidation.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface StringValidator {

    String message() default "Numbers Not Allowed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
