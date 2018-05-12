package com.team12.financialservices.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidation implements ConstraintValidator<StringValidator,String> {

    @Override
    public void initialize(StringValidator field) {
    }



    @Override
    public boolean isValid(String field, ConstraintValidatorContext cxt) {

        if(field == null) {
            return false;
        }
        try {
            Integer.parseInt(field);
            Double.parseDouble(field);
            Float.parseFloat(field);



        }
        catch (NumberFormatException e){
            return true;
        }
        catch (Exception ex){
            return false;
        }

        return false;
    }
}
