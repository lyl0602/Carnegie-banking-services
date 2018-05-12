package com.team12.financialservices.controller.customer;


import com.team12.financialservices.model.Transaction;
import com.team12.financialservices.repository.CustomerRepository;
import com.team12.financialservices.service.customer.CustomerTransactionServices;
import com.team12.financialservices.service.employee.EmployeeAccountServices;
import com.team12.financialservices.service.employee.EmployeeTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerTransactionController {

    @Autowired
    private CustomerTransactionServices customerTransactionServices;

    @Autowired
    private EmployeeTransactionServices employeeTransactionServices;

    @Autowired
    private CustomerRepository customerRepository;



    @RequestMapping(value = "/view_transaction", method = RequestMethod.GET)
    public ModelAndView viewCustomerTransactionHistory(Principal principal)  throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        List<String> messages = new ArrayList<String>();

        List<Transaction> forms = employeeTransactionServices.viewCustomerTransactionHistory(principal.getName());


        if (forms == null) {
            messages.add("No such customer");
        } else {
            modelAndView.addObject("transactionforms", forms);
        }

        modelAndView.addObject("user",customerRepository.findByUserName(principal.getName()));
        modelAndView.addObject("messages", messages);


        modelAndView.setViewName("customer/view_transaction");
        return modelAndView;
    }
}
