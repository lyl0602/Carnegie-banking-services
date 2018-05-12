package com.team12.financialservices.controller.employee;

import com.team12.financialservices.form.CheckForm;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.EmployeeCheckServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeCheckController {

    @Autowired
    private EmployeeCheckServices employeeCheckServices;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/deposit_check", method = RequestMethod.GET)
    public ModelAndView employeeDepositCheckGet()  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        // Double cash, Double balanceAvailable, Double requestOrDepositAmount, String userName
        modelAndView.addObject("form", new CheckForm(null, null, 0.00, null));
        modelAndView.setViewName("employee/deposit_check");
        return modelAndView;
    }

    @RequestMapping(value = "/deposit_check", method = RequestMethod.POST)
    public ModelAndView employeeDepositCheckPost(@ModelAttribute("form") @Valid CheckForm form, BindingResult result)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();


        if (!result.hasErrors()) {

            //do deposit check: check if username exist
            String userName = form.getUserName();
            boolean validUserName = employeeCheckServices.depositCheck(form.getRequestOrDepositAmount(),userName);

            if (!validUserName) {
                result.rejectValue("userName", "error.userName","There is no customer under this username");

            } else {
                DecimalFormat formatter = new DecimalFormat("###,###.###");
                String amount  = formatter.format(form.getRequestOrDepositAmount());
                String successMessage = "You have deposited $" + amount + " of check for user " + form.getUserName() + " successfully";
                modelAndView.addObject("successMessage", successMessage);
                modelAndView.addObject("form", new CheckForm(null, null, 0.00, null));
                modelAndView.setViewName("employee/deposit_check");
                return modelAndView;

            }

        }
        modelAndView.addObject("form",form);
        modelAndView.setViewName("employee/deposit_check");
        return modelAndView;

    }



}
