package com.team12.financialservices.controller.customer;

import com.team12.financialservices.form.BuyFundForm;
import com.team12.financialservices.form.CreateFundForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.customer.BuyFundService;
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
public class BuyFundController {

    private final static Logger LOGGER = Logger.getLogger(BuyFundController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    BuyFundService buyFundService;

    @RequestMapping(value = "/buyFund", method = RequestMethod.POST)
    public ResponseEntity buyFundPost(@Valid @RequestBody BuyFundForm buyFundForm,
                                      BindingResult result,
                                      HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(buyFundForm.toString());

        User user = (User) session.getAttribute("user");

        String message = "";
        // not logged in 403
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not customer 403
        if (user.getRole().equals("Employee")) {
            message = "You must be a customer to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // has error 200
        if (result.hasErrors()) {
            LOGGER.info("BAD REQUEST");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // fund does not exist 200
        if (!buyFundService.fundExist(buyFundForm)) {
            message = "The fund you provided does not exist";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not enough cash in account 200
        if (!buyFundService.enoughCashInAccount(buyFundForm, user)) {
            message = "You don't have enough cash in your account to make this purchase";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not enough cash provided
        if (!buyFundService.enoughCashProvided(buyFundForm)) {
            message = "You didn't provide enough cash to make this purchase";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // success case
        buyFundService.buyFund(buyFundForm, user);
        message = "The fund has been successfully purchased";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
    }
}
