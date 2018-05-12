package com.team12.financialservices.controller.employee;

import com.sun.org.apache.xpath.internal.operations.Mod;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.User;
import com.team12.financialservices.service.employee.EmployeeFundServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeFundController {

    @Autowired
    private EmployeeFundServices employeeFundServices;

    private final static int NAME = 0;
    private final static int SYMBOL = 1;


    @RequestMapping(value = "/create_fund", method = RequestMethod.GET)
    public ModelAndView createFundGet(Model model) {
        model.addAttribute("fund", new Fund());
        return new ModelAndView("employee/create_fund", "model", model);
    }


//    @RequestMapping(value = "/create_fund", method = RequestMethod.POST)
//    public ModelAndView createFundPost(Model model) {
//        Model newModel = employeeFundServices.createFund(model);
//        return new ModelAndView("employee/create_fund", "model", newModel);
//    }

    @RequestMapping(value = "/create_fund", method = RequestMethod.POST)
    public ModelAndView createFundPost
            (@ModelAttribute("fund") @Valid Fund fund,
             BindingResult result)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        // check if the fund has already existed
        // if exists, return null;
        // if not exists, return fund;
        String successMessage = null;
        if (!result.hasErrors()) {
            boolean[] registered = employeeFundServices.createFund(fund, result);
            if (!registered[NAME] && !registered[SYMBOL]) {
                successMessage = "Successfully Created a New Fund!";
                modelAndView.addObject("successMessage", successMessage);
                modelAndView.addObject("fund", new Fund());
            } else {
                if (registered[NAME]) {
                    result.rejectValue("name", "error.name", "This name has already existed.");
                }
                if (registered[SYMBOL]) {
                    result.rejectValue("symbol","error.symbol","This symbol has already existed.");
                }
            }
        }

        modelAndView.setViewName("employee/create_fund");
        return modelAndView;
    }
}
