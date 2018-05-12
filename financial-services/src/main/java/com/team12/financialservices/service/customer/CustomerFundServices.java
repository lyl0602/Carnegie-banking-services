package com.team12.financialservices.service.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.form.FundListForm;
import com.team12.financialservices.form.ResearchFundForm;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.User;

import java.util.List;

public interface CustomerFundServices {

    public boolean buyFund(Long fund_id, Double amountBuy, User user) throws Exception;

    public boolean sellFund(Long fund_id, Double sharesSell, User user) throws Exception;

    public void researchFund() throws Exception;

    public SellFundForm getCustomerSellFundForm(User user, Long fund_id) throws Exception;

    public BuyFundForm getCustomerBuyFundForm(User user, Long fund_id) throws Exception;

    SellFundForm updateCustomerSellFundForm(User user, SellFundForm form) throws Exception;

    BuyFundForm updateCustomerBuyFundForm(User user, BuyFundForm form) throws Exception;

    List<FundListForm> getAllFundForm(User user) throws Exception;

    ResearchFundForm getCustomerResearchFundForm(Long fund_id) throws Exception;
}
