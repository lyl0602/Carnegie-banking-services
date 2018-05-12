package com.team12.financialservices.Validation;

import com.team12.financialservices.form.CreateFundForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CreateFundValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(CreateFundForm.class);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
