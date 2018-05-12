package com.team12.financialservices.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumberValidation implements ConstraintValidator<NumberValidator,Object> {
    @Override
    public void initialize(NumberValidator constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object aDouble, ConstraintValidatorContext constraintValidatorContext) {
        if(aDouble==null)
            return false;


            try {
                Double.parseDouble((String)aDouble);
            }
            catch (NumberFormatException n){
                return false;
            }

//            if(x.matches("^[1-9]\\d*(\\.\\d+)?$")) {
//                return true;
//            }

            return true;


    }
}
