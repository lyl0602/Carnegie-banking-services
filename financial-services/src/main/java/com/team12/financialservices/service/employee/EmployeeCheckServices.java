package com.team12.financialservices.service.employee;

import com.team12.financialservices.model.User;

public interface EmployeeCheckServices {

    public boolean depositCheck(Double amountDeposit, String userName) throws Exception;

}
