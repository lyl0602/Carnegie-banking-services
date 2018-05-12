package com.team12.financialservices.service.employee;

import com.team12.financialservices.form.CreateCustomerAccountForm;

public interface CreateCustomerAccountService {
    public boolean createCustomer(CreateCustomerAccountForm createCustomerAccountForm) throws Exception;
}
