package com.team12.financialservices.service.employee;

import com.team12.financialservices.form.TransitionDayForm;

public interface EmployeeTransitionServices {

    TransitionDayForm getTransitionDayForm() throws Exception;

    TransitionDayForm updateCustomerSellFundForm(TransitionDayForm form) throws Exception;

    void saveTransitionDay(TransitionDayForm form) throws Exception;
}
