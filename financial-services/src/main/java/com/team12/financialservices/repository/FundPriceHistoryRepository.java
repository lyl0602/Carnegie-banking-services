package com.team12.financialservices.repository;

import com.team12.financialservices.model.Fund;
import com.team12.financialservices.model.FundPriceHistory;
import com.team12.financialservices.model.FundPriceHistoryPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface FundPriceHistoryRepository extends JpaRepository<FundPriceHistory, FundPriceHistoryPK> {

    List<FundPriceHistory> findByFundPriceHistoryPK_Fund(Fund fund);

    @Query(value="Select price from fund_price_history where fund_id =?1 and date=(Select max(date) from fund_price_history where fund_id=?1)",nativeQuery = true)
    Double findLatestHistoryPrice(Long id);

    @Query(value="Select max(date) from fund_price_history",nativeQuery = true)
    Date getLatestDate();
}
