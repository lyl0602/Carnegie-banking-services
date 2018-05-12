package com.team12.financialservices.repository;

import com.team12.financialservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUserName(String userName);

    User findById(Long id);


}
