package com.team12.financialservices.Validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

//public interface DateValid {
//}
@Documented
@Constraint(validatedBy = DateValidation.class)
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface DateValid {

    String message() default "Incorrect Date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
