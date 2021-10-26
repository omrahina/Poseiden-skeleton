package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class TradeControllerTest {

    @Mock
    private TradeService tradeService;

    @Spy
    @InjectMocks
    private TradeController tradeController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(tradeController).build();
    }

    @Test
    public void show_trade_list() throws Exception {
        when(tradeService.getAllTrade()).thenReturn(List.of(new Trade()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/trade/list"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKeys("trades", "trade");
    }

    @Test
    public void show_add_trade_form() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void save_trade_and_redirect_trade_list() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        when(tradeService.addTrade(any(Trade.class))).thenReturn(trade);
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .flashAttr("trade", trade))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void save_trade_form_error() throws Exception {
        Trade trade = new Trade();
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/validate")
                .flashAttr("trade", trade))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("trade", "account", "type"))
                .andExpect(view().name("trade/add"));
    }

    @Test
    public void show_update_trade_form() throws Exception {
        Trade trade = new Trade();
        when(tradeService.getTrade(anyInt())).thenReturn(trade);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/trade/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("trade/update"))
                .andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("trade", trade);
    }

    @Test
    public void update_trade_ok() throws Exception {
        Trade trade = new Trade("Trade Account", "Type");
        when(tradeService.updateTrade(any(Trade.class))).thenReturn(trade);
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                .flashAttr("trade", trade))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/trade/list"));
    }

    @Test
    public void update_trade_error() throws Exception {
        Trade trade = new Trade("", "Type");
        mockMvc.perform(MockMvcRequestBuilders.post("/trade/update/1")
                .flashAttr("trade", trade))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("trade", "account"))
                .andExpect(view().name("trade/update"));
    }

    @Test
    public void delete_ruleName_ok() throws Exception {
        doNothing().when(tradeService).deleteTrade(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("/trade/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/trade/list"));
    }
}
