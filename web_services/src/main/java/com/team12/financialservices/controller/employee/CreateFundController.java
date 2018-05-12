package com.team12.financialservices.controller.employee;

import com.team12.financialservices.Validation.CreateFundValidator;
import com.team12.financialservices.controller.customer.BuyFundController;
import com.team12.financialservices.form.CreateFundForm;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.Impl.employee.CreateFundServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class CreateFundController {


    private final static Logger LOGGER = Logger.getLogger(CreateFundController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    CreateFundServiceImpl createFundService;

//    @Autowired
//    CreateFundValidator createFundValidator;


    @RequestMapping(value = "/createFund", method = RequestMethod.POST)
    public ResponseEntity createFundPost(@RequestBody @Valid CreateFundForm createFundForm,
                                         BindingResult result,
                                         HttpSession session) throws Exception {

        LOGGER.setLevel(Level.INFO);
        LOGGER.info(createFundForm.toString());

        User user = (User) session.getAttribute("user");

        String message = "";

        // not logged in 200
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // not admin 200
        if (!user.getRole().equals("Employee")) {
            message = "You must be an employee to perform this action";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

        // has error 400
        if (result.hasErrors()) {
            LOGGER.info("BAD REQUEST");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // logic validation valid 200
        createFundService.createFund(createFundForm);
        message = "The fund was successfully created";
        LOGGER.info(message);
        return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);


    }

}
