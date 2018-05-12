package com.team12.financialservices.service.employee;


import com.team12.financialservices.form.ViewCustomerFormEmp;
import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

public interface EmployeeAccountServices {
    public List<String> changeEmployeePassword(String old_password,
                                               String new_password,
                                               String new_password_confirmation, Authentication authentication)
            throws Exception;

    public List<String> resetCustomerPassword(String userName, String password, String confirmpassword)
            throws Exception;

    public void createEmployeeAccount(User user) throws Exception;

    public void createCustomerAccount(Customer customer) throws Exception;

    public Customer viewCustomerAccount(String customer) throws Exception;


    public User findUserByUserName(String userName) throws Exception;

    public List<ViewCustomerFormEmp> getCustomerOwnFunds(User user) throws Exception;


}

