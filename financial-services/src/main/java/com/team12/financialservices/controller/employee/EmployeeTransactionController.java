package com.team12.financialservices.controller.employee;

import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Transaction;
import com.team12.financialservices.repository.CustomerRepository;
import com.team12.financialservices.service.customer.CustomerFundServices;
import com.team12.financialservices.service.employee.EmployeeAccountServices;
import com.team12.financialservices.service.employee.EmployeeTransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeTransactionController {

    @Autowired
    private CustomerFundServices customerFundServices;
    @Autowired
    private EmployeeAccountServices employeeAccountServices;

    @Autowired
    private EmployeeTransactionServices employeeTransactionServices;
    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/view_customer_transaction/{userName}", method = RequestMethod.GET)
    public ModelAndView viewCustomerTransactionHistory(@PathVariable String userName)  throws Exception{

        ModelAndView modelAndView = new ModelAndView();
        List<String> messages = new ArrayList<String>();
        if (userName == null || userName.trim().equals("")) {
            messages.add("Please Enter username of Customer");

        } else {

            List<Transaction> forms = employeeTransactionServices.viewCustomerTransactionHistory(userName);

            if (forms == null) {
                modelAndView.setViewName("error/404");
                return modelAndView;
            } else {
                modelAndView.addObject("transactionforms", forms);
            }


        }
        modelAndView.addObject("user",customerRepository.findByUserName(userName));
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("employee/view_customer_transaction");


        modelAndView.setViewName("employee/view_customer_transaction");
        return modelAndView;
    }

   /* @RequestMapping(value = "/view_customer_transaction", method = RequestMethod.POST)
    public ModelAndView viewCustomerTransactionHistoryPOST(String username) {

        ModelAndView modelAndView = new ModelAndView();
        List<String> messages = new ArrayList<String>();
        if (username == null || username.trim().equals("")) {
            messages.add("Please Enter username of Customer");

        } else {

            List<Transaction> forms = employeeTransactionServices.viewCustomerTransactionHistory(username);

            if (forms == null) {
                messages.add("No such customer");
            } else {
                modelAndView.addObject("transactionforms", forms);
            }


        }
        modelAndView.addObject("user",customerRepository.findByUserName(username));
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("/employee/view_customer_transaction");
        return modelAndView;
    }*/



}
