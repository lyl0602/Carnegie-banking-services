package com.team12.financialservices.controller;

import com.team12.financialservices.controller.employee.TransitionDayController;
import com.team12.financialservices.form.Message;
import com.team12.financialservices.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.team12.financialservices.form.LoginForm;
import com.team12.financialservices.service.LoginService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.lang.Exception;
import java.security.Principal;
import java.util.logging.Logger;

import com.team12.financialservices.repository.UserRepository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final static Logger LOGGER = Logger.getLogger(MainController.class.getName());

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity loginPost(@Valid @RequestBody LoginForm loginForm,
                                    BindingResult result,
                                    HttpSession session) throws Exception {
        LOGGER.fine(loginForm.toString());

        String message = "";

        // input invalid
        if (result.hasErrors()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        // failure case
        if (!loginService.login(loginForm)) {
            message = "There seems to be an issue with the username/password combination that you entered";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        } else { // successful login
            User user = userRepository.findByUserName(loginForm.getUsername());
            session.setAttribute("user", user);
            message = "Welcome " + user.getFirstName();
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity logoutPost(HttpSession session) throws Exception {
        String message = "";
        User user = (User)session.getAttribute("user");
        if (user == null) {
            message = "You are not currently logged in";
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        } else {
            message = "You have been successfully logged out";
            session.setAttribute("user", null);
            LOGGER.info(message);
            return new ResponseEntity<Message>(new Message(message), HttpStatus.OK);
        }

    }
}