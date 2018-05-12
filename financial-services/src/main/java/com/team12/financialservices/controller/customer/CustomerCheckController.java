package com.team12.financialservices.controller.customer;


import com.team12.financialservices.form.CheckForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.CustomerCheckServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;

@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerCheckController {

    @Autowired
    private CustomerCheckServices customerCheckServices;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/request_check", method = RequestMethod.GET)
    public ModelAndView customerRequestCheckGet(Principal principal)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());

        // form to be displayed
        CheckForm form = customerCheckServices.getCustomerCheckForm(user);
        modelAndView.addObject("form", form);
        modelAndView.setViewName("customer/request_check");
        return modelAndView;

    }

    @RequestMapping(value = "/request_check", method = RequestMethod.POST)
    public ModelAndView customerRequestCheckPost(Principal principal, @ModelAttribute("form") @Valid CheckForm form, BindingResult result)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        User user = userRepository.findByUserName(principal.getName());

        String successMessage;
        if (!result.hasErrors()) {
            boolean validBalanceAvailable = customerCheckServices.RequestCheck(form.getRequestOrDepositAmount(), user);

            if (!validBalanceAvailable) {
//                modelAndView.addObject("successMessage", "You do not have enough balance available");
                result.rejectValue("requestOrDepositAmount", "error.requestOrDepositAmount","You do not have enough balance available");

            } else {
                DecimalFormat formatter = new DecimalFormat("###,###.##");
                String amount  = formatter.format(form.getRequestOrDepositAmount());
                successMessage = "You have requested $" + amount + " of check from your account balance";
                modelAndView.addObject("successMessage", successMessage);
                form = customerCheckServices.getCustomerCheckForm(user);
                modelAndView.addObject("form", form);
                modelAndView.setViewName("customer/request_check");
                return modelAndView;

            }
        }

        // update form to be
        form = customerCheckServices.updateCheckForm(form, user);
        modelAndView.addObject("form", form);
        modelAndView.setViewName("customer/request_check");
        return modelAndView;
    }

}
