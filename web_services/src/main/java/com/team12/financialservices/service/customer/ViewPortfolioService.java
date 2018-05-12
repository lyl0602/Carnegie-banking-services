package com.team12.financialservices.service.customer;

import com.team12.financialservices.form.ViewPortfolioForm;
import com.team12.financialservices.model.User;

import java.util.List;

public interface ViewPortfolioService {

    public List<ViewPortfolioForm> getAllDetails(User user) throws Exception;

}
