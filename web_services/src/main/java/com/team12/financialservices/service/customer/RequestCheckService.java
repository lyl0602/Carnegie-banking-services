package com.team12.financialservices.service.customer;

import com.team12.financialservices.form.RequestCheckForm;
import com.team12.financialservices.model.User;

public interface RequestCheckService {

    boolean enoughCashInAccount(RequestCheckForm requestCheckForm, User user) throws Exception;

    void requestCheck(RequestCheckForm requestCheckForm, User user) throws Exception;
}
