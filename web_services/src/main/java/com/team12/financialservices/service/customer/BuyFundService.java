package com.team12.financialservices.service.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.model.User;

import javax.validation.Valid;

public interface BuyFundService {
    boolean fundExist(BuyFundForm buyFundForm) throws Exception;

    boolean enoughCashInAccount(BuyFundForm buyFundForm, User user) throws Exception;

    boolean enoughCashProvided(BuyFundForm buyFundForm) throws Exception;

    void buyFund(BuyFundForm buyFundForm, User user) throws Exception;
}
