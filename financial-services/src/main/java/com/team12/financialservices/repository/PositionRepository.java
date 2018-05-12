package com.team12.financialservices.repository;

import com.team12.financialservices.model.Customer;
import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.PositionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, PositionPK> {

    List<Position> findByPositionPK_Customer (Customer customer);
    @Query(value = "Select * from position where customer_id=?1 and shares>0",nativeQuery = true)
    List<Position> findBySharesAndCustomerId(Long id);

    Position findByPositionPK(PositionPK positionPK);



}
