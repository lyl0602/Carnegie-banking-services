package com.team12.financialservices.service.employee;

import com.team12.financialservices.form.CreateFundForm;
import org.springframework.validation.Errors;

import javax.validation.Valid;

public interface CreateFundService {
    boolean createFund(CreateFundForm createFundForm) throws Exception;

//    public boolean hasErrors(@Valid CreateFundForm createFundForm, Errors error) throws Exception;

}

