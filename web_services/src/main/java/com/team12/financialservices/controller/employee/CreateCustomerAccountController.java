package com.team12.financialservices.controller.employee;

import com.team12.financialservices.controller.customer.BuyFundController;
import com.team12.financialservices.form.CreateCustomerAccountForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.Impl.employee.CreateCustomerAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CreateCustomerAccountController {

    private final static Logger LOGGER = Logger.getLogger(CreateCustomerAccountController.class.getName());

    @Autowired
    UserRepository userRepository;
    @Autowired
    CreateCustomerAccountServiceImpl customerAccountService;


    @RequestMapping(value = "/createCustomerAccount", method = RequestMethod.POST)
    public ResponseEntity createCustomerAccount(@Valid @RequestBody CreateCustomerAccountForm createCustomerAccountForm,
                                                BindingResult result,
                                                HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(createCustomerAccountForm.toString());

        String message = "";
//        ResponseEntity responseEntity = new ResponseEntity();
        User user = (User) session.getAttribute("user");

        // not logged in 200
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        if (!user.getRole().equals("Employee")) {
            message = "You must be an employee to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        if (result.hasErrors()) {
            LOGGER.info("BAD REQUEST");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        if (customerAccountService.createCustomer(createCustomerAccountForm)) {
            message = createCustomerAccountForm.getFname() + " was registered successfully";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        } else {
            message = "The input you provided is not valid";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

    }


}
