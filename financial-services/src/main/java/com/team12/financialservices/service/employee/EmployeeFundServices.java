package com.team12.financialservices.service.employee;

import com.team12.financialservices.model.Fund;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

public interface EmployeeFundServices {

    public boolean[] createFund(Fund fund, BindingResult result) throws Exception;


}
