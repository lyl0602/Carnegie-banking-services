package com.team12.financialservices.repository;

import com.team12.financialservices.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
