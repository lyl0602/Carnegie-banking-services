package com.team12.financialservices.controller.customer;

import com.team12.financialservices.form.ChangePassword;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.form.ViewCustomerFormEmp;
import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.TransactionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.EmployeeAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/customer")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerAccountController {

    @Autowired
    private EmployeeAccountServices employeeAccountServices;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/view_customer_details",method = RequestMethod.GET)
    public ModelAndView viewCustomer(Principal principal) throws Exception{
        ModelAndView modelAndView = new ModelAndView();


        User cust = employeeAccountServices.findUserByUserName(principal.getName());
        List<ViewCustomerFormEmp> forms = employeeAccountServices.getCustomerOwnFunds(cust);

        Customer cust1 = employeeAccountServices.viewCustomerAccount(cust.getUserName());
        List<String> messages = new ArrayList<>();
        Date execute_date = transactionRepository.getLatestDateofCustomer(cust1.getId());

        if(cust!=null){
            messages.add("Details of"+cust1.getFirstName()+" "+cust1.getLastName()+"are following:");
            modelAndView.addObject("user",cust1);
            modelAndView.addObject("fundform",forms);
            modelAndView.addObject("messages",messages);

            if(execute_date==null) {
                modelAndView.addObject("executeDate","");
            }
            else{
                modelAndView.addObject("executeDate",execute_date);
            }


        }




        modelAndView.setViewName("customer/view_customer_details");
        return modelAndView;
    }

    @RequestMapping(value = "/change_password_customer", method = RequestMethod.GET)
    public ModelAndView changePasswordEmployeeGet(Principal principal)  throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());
        if(user == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }
        ChangePassword form = new ChangePassword();
        modelAndView.addObject("form",form);
        modelAndView.setViewName("customer/change_password_customer");

        return modelAndView;
    }


//TODO customer own password change


    @RequestMapping(value = "/change_password_customer", method = RequestMethod.POST)
    public ModelAndView changePasswordEmployeePost(@ModelAttribute("form") @Valid ChangePassword form,BindingResult bindingResult,
                                                   Authentication authentication)  throws Exception{

        ModelAndView modelAndView = new ModelAndView();

        String old_password = form.getOld_password();
        String new_password = form.getNew_password();
        String new_password_confirmation = form.getNew_password_confirmation();
        if(bindingResult.hasErrors()){
           modelAndView.setViewName("customer/change_password_customer");
        }
        else{
            List<String> messages = employeeAccountServices.changeEmployeePassword(old_password, new_password, new_password_confirmation, authentication);



            if (messages != null && messages.size() > 0) {
                modelAndView.addObject("messages",messages);
            }
           // modelAndView.addObject("form",new ChangePassword());
            //modelAndView.addObject("messages","Updated Successfully");
            modelAndView.setViewName("customer/change_password_customer");

        }


        return modelAndView;
        //return new ModelAndView("employee/change_password_employee", "model", model);
    }

}
