package com.team12.financialservices.controller.customer;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.form.ViewPortfolioForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.service.customer.ViewPortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class ViewPortfolioController {

    private final static Logger LOGGER = Logger.getLogger(ViewPortfolioController.class.getName());

    @Autowired
    private ViewPortfolioService viewPortfolioService;

    @RequestMapping(value = "/viewPortfolio", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Object> getAll(HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.fine("View Portfolio: " + (User) session.getAttribute("user"));

        String message = "";
        User user = (User) session.getAttribute("user");
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<>(new Message(message), HttpStatus.OK);
        }
        if (user.getRole().equals("Employee")) {
            message = "You must be a customer to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<>(new Message(message), HttpStatus.OK);
        }

        List<ViewPortfolioForm> forms = viewPortfolioService.getAllDetails(user);
        if (forms == null || forms.size() == 0) {
            message = "You don't have any funds in your Portfolio";
            LOGGER.info(message);
            return new ResponseEntity<>(new Message(message), HttpStatus.OK);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        DecimalFormat df = new DecimalFormat("0.00");
        map.put("message", "The action was successful");
        map.put("cash", df.format(user.getCash()));
        map.put("funds", forms);
        LOGGER.info("The action was successful");
        return new ResponseEntity<>(map, HttpStatus.OK);


//        List<JSONPObject> entities = new ArrayList<>();


    }
}
