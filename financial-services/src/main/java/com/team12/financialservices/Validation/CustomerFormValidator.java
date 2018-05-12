package com.team12.financialservices.Validation;

import com.team12.financialservices.model.Customer;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.ConstraintViolation;
//import javax.validation.Validator;
import java.util.Set;

@Component
public class CustomerFormValidator  {

// Validator   @Override
//    public boolean supports(Class<?> aClass) {
//        return aClass.isAssignableFrom(Customer.class);
//    }
//
//    @Override
//    public void validate(Object o, Errors errors) {
//
//        Customer c = new Customer();
//        Double cash =0.0;
//        try {
//            cash = Double.parseDouble(c.getCash());
//        }
//        catch (Exception e) {
//            errors.rejectValue("cash","error.amount.valid");
//            return;
//        }
//        if (cash <= 0) {
//            errors.rejectValue("amount", "error.amount.invalid");
//            return;
//        }
//
//    }
}
