package com.team12.financialservices.controller.employee;

import com.team12.financialservices.controller.customer.BuyFundController;
import com.team12.financialservices.form.DepositCheckForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.employee.DepositCheckService;
import com.team12.financialservices.service.employee.TransitionDayService;

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

import org.springframework.web.bind.annotation.RestController;

import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class TransitionDayController {

    private final static Logger LOGGER = Logger.getLogger(TransitionDayController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransitionDayService transitionDayService;

    @RequestMapping(value = "/transitionDay", method = RequestMethod.POST)
    public ResponseEntity transitionDayPost(HttpSession session) throws Exception {

        Handler consoleHandler = new ConsoleHandler();
//			LOGGER.addHandler(consoleHandler);
//			consoleHandler.setLevel(Level.INFO);
        LOGGER.setLevel(Level.INFO);
//			LOGGER.config("");

        LOGGER.info("Transition Day: " + (User) session.getAttribute("user"));

        User user = (User) session.getAttribute("user");

        String message = "";
        // not logged in 200
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not employee 200
        if (!user.getRole().equals("Employee")) {
            message = "You must be an employee to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // success case 200
        transitionDayService.transitionDay();
        message = "The fund prices have been successfully recalculated";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
    }

}
