package com.team12.financialservices.controller;

import com.team12.financialservices.service.CustomeUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@RestController
public class MainController {
    @Autowired
    private CustomeUserServices customeUserServices;

    private final static String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";
    private final static String ROLE_CUSTOMER = "ROLE_CUSTOMER";


    @RequestMapping(value = "/dispatch", method = RequestMethod.GET)
    public String dispatch(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equalsIgnoreCase(ROLE_EMPLOYEE)) {
                return "redirect:/employee/customer_list";
//                return "employee/index_employee";
            }
            if (authority.getAuthority().equalsIgnoreCase(ROLE_CUSTOMER)) {
//                return "customer/view_customer_details";
                return "redirect:/customer/view_customer_details";
            }
        }
        return "login";
    }


//    @RequestMapping(value = "/employee/index_employee" , method = RequestMethod.GET)
//public ModelAndView indexEmployee(Model model) {
//        return new ModelAndView("/employee/index_employee", "indexEmployee", model);
//}

    @GetMapping("/employee/index_employee")
    public String indexEmployee() {
        return "employee/index_employee";
    }

    @GetMapping("/customer/index_customer")
    public String indexCustomer() {
        return "customer/index_customer";
    }


}
