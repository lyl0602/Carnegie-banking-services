package com.team12.financialservices.repository;

import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{


    Transaction findTopByExecuteDate(Long customerId, Long fundId);

    List<Transaction> findByCustomerId(Long id);

    @Query(value="Select max(execute_date) from transaction",nativeQuery = true)
    Date getLatestDate();
    @Query(value="Select max(execute_date) from transaction where customer_id=?1",nativeQuery = true)
    Date getLatestDateofCustomer(Long id);

    List<Transaction> findByTransactionTypeAndExecuteDate(Transaction.TransactionType transactionType, Date date);


    List<Transaction> findByTransactionTypeAndFundAndExecuteDate(Transaction.TransactionType transactionType, Fund fund, Date date);

}
