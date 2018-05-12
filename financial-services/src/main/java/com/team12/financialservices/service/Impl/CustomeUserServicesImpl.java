package com.team12.financialservices.service.Impl;

import com.team12.financialservices.model.User;
import com.team12.financialservices.repository.UserRepository;
import com.team12.financialservices.service.CustomeUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@Transactional
public class CustomeUserServicesImpl implements CustomeUserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) {

        User user = userRepository.findByUserName(userName);
        if(user == null){
            throw new UsernameNotFoundException("User does not exist");
        }
//        // add user's role
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for(Role role:user.getRoles())
//        {
//            System.out.println(role.getName());
//            authorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
        // add user's role

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(), Collections.singletonList(authority));
    }





}
