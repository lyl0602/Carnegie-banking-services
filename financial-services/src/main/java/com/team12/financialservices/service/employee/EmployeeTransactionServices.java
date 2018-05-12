package com.team12.financialservices.service.employee;

import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Transaction;

import java.util.List;

public interface EmployeeTransactionServices {

    public List<Transaction> viewCustomerTransactionHistory(String username) throws Exception;
}
