package com.team12.financialservices.service.Impl;

import com.team12.financialservices.form.LoginForm;
import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean login(LoginForm loginForm) {
        User user = userRepository.findByUserName(loginForm.getUsername());
        if (user == null) {
            return false;
        }
        if (user.getPassword().equals(loginForm.getPassword())) {
            return true;
        } else {
            return false;
        }
    }
}
