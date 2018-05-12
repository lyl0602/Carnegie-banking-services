package com.team12.financialservices.service.customer;

import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.User;

import javax.validation.Valid;

public interface SellFundService {
    boolean fundExist(SellFundForm sellFundForm) throws Exception;

    boolean enoughSharesInAccount(SellFundForm sellFundForm, User user) throws Exception;

    void sellFund(SellFundForm sellFundForm, User user) throws Exception;
}
