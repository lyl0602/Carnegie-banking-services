package com.team12.financialservices.controller.employee;


import com.team12.financialservices.form.ResearchFundForm;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.form.TransitionDay;
import com.team12.financialservices.form.TransitionDayForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.repository.TransactionRepository;
import com.team12.financialservices.repository.FundRepository;
import com.team12.financialservices.service.employee.EmployeeTransitionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeTransitionController {


    @Autowired
    private FundRepository fundRepository;

    @Autowired
    private EmployeeTransitionServices employeeTransitionServices;

    @Autowired
    private TransactionRepository transactionRepository;

    @RequestMapping(value = "/transition_day", method = RequestMethod.GET)
    public ModelAndView transitionDayGet(TransitionDay transitionDay)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        // value to be displayed

        TransitionDayForm form = employeeTransitionServices.getTransitionDayForm();
        modelAndView.addObject("form", form);
        modelAndView.setViewName("employee/transition_day");
        return modelAndView;
    }


    @RequestMapping(value = "/transition_day", method = RequestMethod.POST)
    public ModelAndView transitionDayPost(@ModelAttribute("form") @Valid TransitionDayForm form,
                                          BindingResult result)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();

        String message = null;
        if (result.hasErrors()) {
            // update form
            form = employeeTransitionServices.updateCustomerSellFundForm(form);
            // fund id is null, get the new form
            if (form == null) {
                message = "fund id is null or not valid";
                modelAndView.addObject("message", message);
                modelAndView.addObject("form", employeeTransitionServices.getTransitionDayForm());
            } else { // if fund id is not null, update the form
                modelAndView.addObject("form", form);
            }

//        } else if (!employeeTransitionServices.validDate(form.getDate())){ // valid date
//            message = "date is not valid";
//            modelAndView.addObject("message", message);
//            modelAndView.addObject("form", employeeTransitionServices.getTransitionDayForm());

        }else {
            message = "You have successfully updated the transition day";
            modelAndView.addObject("message", message);

            // save to db
            employeeTransitionServices.saveTransitionDay(form);

            // updated form to be displayed
            TransitionDayForm newForm = employeeTransitionServices.getTransitionDayForm();

            modelAndView.addObject("form", newForm);
        }

        modelAndView.setViewName("employee/transition_day");
        return modelAndView;
    }

}


