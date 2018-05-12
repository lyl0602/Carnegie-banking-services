package com.team12.financialservices.service.customer;


import com.team12.financialservices.form.CheckForm;
import com.team12.financialservices.model.User;
import org.hibernate.annotations.Check;

import javax.validation.Valid;

public interface CustomerCheckServices {

    public boolean RequestCheck(Double amountRequest, User user) throws Exception;
    public CheckForm getCustomerCheckForm(User user) throws Exception;

    CheckForm updateCheckForm(CheckForm form, User user) throws Exception;
}
