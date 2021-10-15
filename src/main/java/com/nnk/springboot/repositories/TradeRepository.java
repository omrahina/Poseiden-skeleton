package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import jdk.jfr.Registered;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {

    Trade findTradeByTradeId(int id);
}
