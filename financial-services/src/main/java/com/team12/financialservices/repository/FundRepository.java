package com.team12.financialservices.repository;

import com.team12.financialservices.model.Fund;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FundRepository extends JpaRepository<Fund, Long>{
    Fund findByName (String name);
    Fund findBySymbol (String symbol);
    Fund findById (Long id);

    List<Fund> findAll();
}
