package com.team12.financialservices.controller.employee;

import com.team12.financialservices.controller.customer.BuyFundController;
import com.team12.financialservices.form.DepositCheckForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.LoginService;
import com.team12.financialservices.service.employee.DepositCheckService;
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
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class DepositCheckController {

    private final static Logger LOGGER = Logger.getLogger(DepositCheckController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    DepositCheckService depositCheckService;

    @RequestMapping(value = "/depositCheck", method = RequestMethod.POST)
    public ResponseEntity depositCheckPost(@Valid @RequestBody DepositCheckForm depositCheckForm, BindingResult result, HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(depositCheckForm.toString());

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

        // has error 400






        if (result.hasErrors() ||
                userRepository.findByUserName(depositCheckForm.getUsername()) == null ||
                depositCheckForm.getUsername().equals("jadmin")) {

            LOGGER.info("BAD REQUEST");

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // success case 200
        depositCheckService.depositCheck(depositCheckForm);
        message = "The check was successfully deposited";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
    }
}
