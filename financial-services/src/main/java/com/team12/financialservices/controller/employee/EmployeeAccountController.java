package com.team12.financialservices.controller.employee;


import com.team12.financialservices.Validation.StringValidator;
import com.team12.financialservices.form.ChangePassword;
import com.team12.financialservices.form.ViewCustomerFormEmp;
import com.team12.financialservices.model.*;
import com.team12.financialservices.repository.CustomerRepository;
import com.team12.financialservices.repository.TransactionRepository;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.CustomerFundServices;
import com.team12.financialservices.service.employee.EmployeeAccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.HttpRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmployeeAccountController {

    @Autowired
    private CustomerFundServices customerFundServices;
    @Autowired
    private EmployeeAccountServices employeeAccountServices;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionRepository transactionRepository;

//    @Autowired
//    private StringValidator stringValidator;

    @RequestMapping(value = "/change_password_employee", method = RequestMethod.GET)
    public ModelAndView changePasswordEmployeeGet(Principal principal) {

        ModelAndView modelAndView = new ModelAndView();
        User user = userRepository.findByUserName(principal.getName());
        if (user == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }
        ChangePassword form = new ChangePassword();
        modelAndView.addObject("form", form);
        modelAndView.setViewName("employee/change_password_employee");

        return modelAndView;


        //return new ModelAndView("employee/change_password_employee");
    }


    //    public ModelAndView changePasswordEmployeePost(String old_password,
//                                                   String new_password,
//                                                   String new_password_confirmation,
//                                                    Authentication authentication) {
//        List<String> messages = employeeAccountServices.changeEmployeePassword(old_password, new_password, new_password_confirmation, authentication);
//        ModelAndView modelAndView = new ModelAndView();
//
//
//
//        if (messages != null && messages.size() > 0) {
//            modelAndView.addObject("messages",messages);
//        }
//        modelAndView.setViewName("employee/change_password_employee");
//        return modelAndView;
//        //return new ModelAndView("employee/change_password_employee", "model", model);
//    }
    @RequestMapping(value = "/change_password_employee", method = RequestMethod.POST)
    public ModelAndView changePasswordEmployeePost(@ModelAttribute("form") @Valid ChangePassword form, BindingResult bindingResult,
                                                   Authentication authentication) throws Exception {

        ModelAndView modelAndView = new ModelAndView();

        String old_password = form.getOld_password();
        String new_password = form.getNew_password();
        String new_password_confirmation = form.getNew_password_confirmation();
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("employee/change_password_employee");
        } else {
            List<String> messages = employeeAccountServices.changeEmployeePassword(old_password, new_password, new_password_confirmation, authentication);


            if (messages != null && messages.size() > 0) {
                modelAndView.addObject("messages", messages);
            }
            // modelAndView.addObject("form",new ChangePassword());
            //modelAndView.addObject("messages","Updated Successfully");
            modelAndView.setViewName("employee/change_password_employee");

        }


        return modelAndView;
    }


    @RequestMapping(value = "/reset_customer_password/{userName}", method = RequestMethod.GET)
    public ModelAndView resetCustomerPasswordGet(@PathVariable String userName) {
        ModelAndView modelAndView = new ModelAndView();
        User c = userRepository.findByUserName(userName);
        if(c==null) {
            modelAndView.setViewName("employee/reset_customer_password");
        }

        else {
            modelAndView.setViewName("employee/reset_customer_password");
            modelAndView.addObject("username", userName);
        }



        return modelAndView;
    }

    @RequestMapping(value = "/reset_customer_password", method = RequestMethod.POST)
    public ModelAndView resetCustomerPasswordPost(String username, String password, String confirmpassword, Model model) throws Exception {

        ModelAndView modelAndView = new ModelAndView();
        List<String> messages = employeeAccountServices.resetCustomerPassword(username, password, confirmpassword);
        if (messages != null && messages.size() > 0) {
            model.addAttribute("messages", messages);
        }
        // model.addAttribute("messages",messages);
        modelAndView.addObject("messages", messages);
        modelAndView.setViewName("employee/reset_customer_password");
        return modelAndView;
    }


    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();

        User employee = new User();
        modelAndView.addObject("user", employee);
        modelAndView.setViewName("employee/registration");
        return modelAndView;

    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registerNewEmployee(@Valid User emp, BindingResult bindingResult) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //User user = new User();
        //emp.ge
        //   emp.setBlanceAvailable = ();


        User emp1 = employeeAccountServices.findUserByUserName(emp.getUserName());
        if (emp1 != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (!emp.getPassword().equals(emp.getConfirmpassword())) {
            bindingResult
                    .rejectValue("password", "error.user", "Password Should Match");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("employee/registration");
        } else {
            employeeAccountServices.createEmployeeAccount(emp);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("employee/registration");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/registration_customer", method = RequestMethod.GET)
    public ModelAndView registerCustomer() {
        ModelAndView modelAndView = new ModelAndView();
        User cust = new User();
        Customer customer = new Customer();

        modelAndView.addObject("customer", customer);
        //modelAndView.addObject("user",cust);
        modelAndView.setViewName("employee/registration_customer");
        return modelAndView;

    }


//    @InitBinder
//    public void dataBinding(WebDataBinder binder) {
//  //      binder.addValidators(userValidator, emailValidator);
////        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
////        dateFormat.setLenient(false);
//        String s="";
//        Double x = Double.parseDouble(s);
//        binder.registerCustomEditor(Double.class, "dob", new CustomNumberEditor(x,true));
//    }


    @RequestMapping(value = "/registration_customer", method = RequestMethod.POST)
    public ModelAndView createNewCustomer(@Valid Customer emp, BindingResult bindingResult, HttpServletRequest request) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        //User user = new User();
        //emp.ge
        //  avail=emp.getCash();
//    System.out.println(emp.getPassword());
//    System.out.println(emp.getConfirmpassword());
//    System.out.println(emp.ge);
        emp.setBalanceAvailable(emp.getCash());
        User emp1 = employeeAccountServices.findUserByUserName(emp.getUserName());
        // emp1.setBalanceAvailable(emp.getCash());
//        try {
//            Double d = Double.parseDouble(request.getParameter("cash"));
//        } catch (NumberFormatException e) {
//            bindingResult.rejectValue("cash", "error.user", "Please Enter amount");
//        }


        if (emp1 != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (!emp.getPassword().equals(emp.getConfirmpassword())) {
            bindingResult
                    .rejectValue("password", "error.user", "Password Should Match");
        }
//        if (emp.getCash() == null || emp.getCash() > 1000000 || emp.getCash() < 0) {
//            bindingResult
//                    .rejectValue("cash", "error.user", "Amount range is 1 to 1,000,000");
//        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("employee/registration_customer");
        } else if (emp1 == null) {
            employeeAccountServices.createCustomerAccount(emp);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            //modelAndView.addObject("user", new User());
            modelAndView.addObject("customer", new Customer());
            modelAndView.setViewName("employee/registration_customer");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/view_customer/{userName}", method = RequestMethod.GET)
    public ModelAndView viewCustomer(@PathVariable String userName) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        User cust = customerRepository.findByUserName(userName);
        List<ViewCustomerFormEmp> forms = employeeAccountServices.getCustomerOwnFunds(cust);

        Customer cust1 = employeeAccountServices.viewCustomerAccount(userName);
        List<String> messages = new ArrayList<>();

        Date execute_date = transactionRepository.getLatestDateofCustomer(cust.getId());


        if (cust != null && cust1 != null) {

//<<<<<<< HEAD
            // messages.add("Details of"+cust1.getFirstName()+" "+cust1.getLastName()+"are following:");

            //  messages.add("Details of"+cust1.getFirstName()+" "+cust1.getLastName()+"are following:");>>>>>>> 1847f0020e8ee50b3d73d9f622a03a89f4ec3e86
//=======
//>>>>>>> b9969f93ab9016fa5b8d63fe9a3fbf4db542c895
            modelAndView.addObject("user", cust1);
            modelAndView.addObject("fundform", forms);
            modelAndView.addObject("messages", messages);
            //  modelAndView.addObject("executeDate","");
            modelAndView.addObject("executeDate", execute_date);


        } else {
            modelAndView.setViewName("error/404");
            return modelAndView;
            //messages.add("No such Customer");

           /* Collection<Transaction> t = cust.getTransactions();
            for (Transaction t1:t) {


            }
            Collection<Position> p = cust.getPositions();
            for()*/


        }


        modelAndView.addObject("messages", messages);

        modelAndView.setViewName("employee/view_customer");
        return modelAndView;
    }

   /* @RequestMapping(value = "/view_customer/",method = RequestMethod.POST)
    public ModelAndView viewCustomerPost(String username){
        ModelAndView modelAndView = new ModelAndView();
        //Customer cust = employeeAccountServices.viewCustomerAccount(username);
        User cust = employeeAccountServices.findUserByUserName(username);
        List<ViewCustomerFormEmp> forms = employeeAccountServices.getCustomerOwnFunds(cust);

        Customer cust1 = employeeAccountServices.viewCustomerAccount(username);
        List<String> messages = new ArrayList<>();

        if(cust!=null){
            messages.add("Details of"+cust1.getFirstName()+" "+cust1.getLastName()+"are following:");
            modelAndView.addObject("user",cust1);
            modelAndView.addObject("fundform",forms);
            modelAndView.addObject("messages",messages);

        }
        else {
            messages.add("No such Customer");

           /* Collection<Transaction> t = cust.getTransactions();
            for (Transaction t1:t) {


            }
            Collection<Position> p = cust.getPositions();
            for()




        }



        modelAndView.addObject("messages",messages);

        return modelAndView;


    }*/


    @RequestMapping(value = "/customer_list", method = RequestMethod.GET)
    public ModelAndView customerList() {
        ModelAndView modelAndView = new ModelAndView();

        List<Customer> users = customerRepository.findAll();

        if (users == null) {
            modelAndView.setViewName("error/404");
            return modelAndView;
        }

        modelAndView.addObject("users", users);
        modelAndView.setViewName("employee/customer_list");


        return modelAndView;


    }


}

