package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;

    @Test
    public void return_all_trades(){
        when(tradeRepository.findAll()).thenReturn(List.of(new Trade(), new Trade()));
        List<Trade> trades = tradeService.getAllTrade();

        assertThat(trades).hasSize(2);
    }

    @Test
    public void return_empty_trade_list(){
        when(tradeRepository.findAll()).thenReturn(List.of());
        List<Trade> trades = tradeService.getAllTrade();

        assertThat(trades).isEmpty();
    }

    @Test
    public void get_trade_ok(){
      when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(new Trade());
      Trade trade = tradeService.getTrade(1);

        assertThat(trade).isNotNull();
    }

    @Test
    public void get_trade_failure(){
        when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(null);
        Trade trade = tradeService.getTrade(1);

        assertThat(trade).isNull();
    }

    @Test
    public void add_trade_ok(){
        Trade trade = new Trade("Trade Account", "Type");
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade);
        Trade savedTrade = tradeService.addTrade(trade);

        assertThat(savedTrade).isNotNull();
    }

    @Test
    public void update_trade_ok(){
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);
        Trade trade2 = new Trade("Updated Trade Account", "Type");
        when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(trade);
        when(tradeRepository.save(any(Trade.class))).thenReturn(trade2);
        Trade updatedTrade = tradeService.updateTrade(trade);

        assertThat(updatedTrade.getAccount()).isEqualTo("Updated Trade Account");
    }

    @Test
    public void update_trade_failure(){
        Trade trade = new Trade("Trade Account", "Type");
        trade.setTradeId(1);
        when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(null);
        Trade updatedTrade = tradeService.updateTrade(trade);

        assertThat(updatedTrade).isNull();
    }

    @Test
    public void delete_trade_ok(){
        Trade trade = new Trade("Trade Account", "Type");
        when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(trade);
        doNothing().when(tradeRepository).delete(any(Trade.class));

        assertThat(tradeService.deleteTrade(1)).isTrue();
    }

    @Test
    public void delete_trade_failure(){
        when(tradeRepository.findTradeByTradeId(anyInt())).thenReturn(null);

        assertThat(tradeService.deleteTrade(1)).isFalse();
    }
}
