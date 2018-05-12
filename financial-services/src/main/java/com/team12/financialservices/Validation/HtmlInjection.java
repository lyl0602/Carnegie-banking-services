package com.team12.financialservices.Validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
//import java.lang.annotation.Documented;

@Documented
@Constraint(validatedBy = HtmlInjectionValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface HtmlInjection {

    String message() default "Incorrect Field Values, Don't Attack";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
