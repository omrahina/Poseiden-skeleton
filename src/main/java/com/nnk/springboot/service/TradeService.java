package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    public List<Trade> getAllTrade(){
        List<Trade> trades = tradeRepository.findAll();
        if (!trades.isEmpty()){
            log.info("Trade(s) found.");
            return trades;
        }
        log.error("No Trade found.");
        return List.of();
    }

    public Trade getTrade(int id){
        Trade trade = tradeRepository.findTradeByTradeId(id);
        if (trade != null){
            log.info("Trade found.");
            return trade;
        }
        log.error("No Trade found.");
        return null;
    }

    public Trade addTrade(Trade trade){
        try{
            Trade savedTrade = tradeRepository.save(trade);
            log.info("Trade successfully saved.");
            return savedTrade;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public Trade updateTrade(Trade trade){
        Trade tradeToUpdate = tradeRepository.findTradeByTradeId(trade.getTradeId());
        if (tradeToUpdate != null){
            log.info("Update in progress ...");
            tradeToUpdate.setAccount(trade.getAccount());
            tradeToUpdate.setType(trade.getType());
            tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());
            return tradeRepository.save(tradeToUpdate);
        }
        log.error("Unable to update the Trade.");
        return null;
    }

    public void deleteTrade(int id){
        Trade trade = tradeRepository.findTradeByTradeId(id);
        if (trade != null){
            tradeRepository.delete(trade);
            log.info("Trade successfully deleted.");
        } else {
            log.error("Deletion failure");
        }
    }
}
