package com.team12.financialservices.controller.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.form.SellFundForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.SellFundService;
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
public class SellFundController {

    private final static Logger LOGGER = Logger.getLogger(SellFundController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellFundService sellFundService;

    @RequestMapping(value = "/sellFund", method = RequestMethod.POST)
    public ResponseEntity buyFundPost(@Valid @RequestBody SellFundForm sellFundForm,
                                      BindingResult result,
                                      HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(sellFundForm.toString());

        User user = (User) session.getAttribute("user");

        String message = "";
        // not logged in 403
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not admin 403
        if (!user.getRole().equals("Customer")) {
            message = "You must be a customer to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // has error 400
        if (result.hasErrors() || Integer.valueOf(sellFundForm.getNumShares()) == 0) {
            LOGGER.info("BAD REQUEST");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // fund does not exist 200
        if (!sellFundService.fundExist(sellFundForm)) {
            message = "The fund you provided does not exist";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not enough shares in account 200
        if (!sellFundService.enoughSharesInAccount(sellFundForm, user)) {
            message = "You don't have that many shares in your portfolio";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // success case
        sellFundService.sellFund(sellFundForm, user);
        message = "The shares have been successfully sold";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
    }
}
