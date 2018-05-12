package com.team12.financialservices.controller.customer;

import com.team12.financialservices.form.Message;
import com.team12.financialservices.form.RequestCheckForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.RequestCheckService;
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
public class RequestCheckController {

    private final static Logger LOGGER = Logger.getLogger(RequestCheckController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    RequestCheckService requestCheckService;

    @RequestMapping(value = "/requestCheck", method = RequestMethod.POST)
    public ResponseEntity requestCheckPost(@Valid @RequestBody RequestCheckForm requestCheckForm, BindingResult result, HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(requestCheckForm.toString());

        User user = (User) session.getAttribute("user");

        String message = "";
        // not logged in 200
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not customer 200
        if (!user.getRole().equals("Customer")) {
            message = "You must be a customer to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // has error 400
        if (result.hasErrors()) {
            LOGGER.info("BAD REQUEST");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // not enough cash in account 200
        if (!requestCheckService.enoughCashInAccount(requestCheckForm, user)) {
            message = "You don't have sufficient funds in your account to cover the requested check";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // success case
        requestCheckService.requestCheck(requestCheckForm, user);
        message = "The check has been successfully requested";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
    }
}
