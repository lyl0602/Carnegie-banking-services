package com.team12.financialservices.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HtmlInjectionValidator implements ConstraintValidator<HtmlInjection,String>
{

    @Override
    public void initialize(HtmlInjection field) {
    }



    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {

        if(field.matches(".*[<>\\\"].*")) {
            return false;
        }

       return true;
    }
}
