package com.team12.financialservices.repository;

import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.Position;
import com.team12.financialservices.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {

    Position findByFundAndUser(Fund fund, User user);
    List<Position> findByUser(User user);


}
