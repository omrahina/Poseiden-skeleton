package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
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
public class BidListControllerTest {

    @Mock
    private BidListService bidListService;

    @Spy
    @InjectMocks
    private BidListController bidListController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(bidListController).build();
    }

    @Test
    public void show_bids_ok() throws Exception {
        when(bidListService.findAllBids()).thenReturn(List.of(new BidList("Account Test", "Type Test", 10d)));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list.html"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKeys("bidList", "bid");
    }

    @Test
    public void show_home_empty_bids() throws Exception {
        when(bidListService.findAllBids()).thenReturn(List.of());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list.html"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKey("bid")
        .doesNotContainKey("bidList");
    }

    @Test
    public void show_home_error_bidId() throws Exception {
        when(bidListService.findAllBids()).thenReturn(List.of(new BidList("Account Test", "Type Test", 10d)));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list.html").param("error", "error"))
                .andExpect(status().isOk())
                .andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("error", "No bid found for the specified id.");
    }

    @Test
    public void show_add_bid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/add"))
                .andExpect(status().isOk());
    }

    @Test
    public void save_bidList_and_redirect_bidList_list() throws Exception {
       BidList bid = new BidList("Account Test", "Type Test", 10d);
       when(bidListService.addBidList(any())).thenReturn(bid);
       mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
               .flashAttr("bid", bid))
               .andExpect(status().isFound())
               .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void save_bidList_form_errors() throws Exception {
        BidList bid = new BidList();
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/validate")
                .flashAttr("bid", bid))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("bid", "account", "type"))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    public void show_update_form() throws Exception {
        BidList bid = new BidList();
        when(bidListService.getBidList(anyInt())).thenReturn(bid);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("bidList/update")).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("bidList", bid);
    }

    @Test
    public void show_update_form_bidList_not_found() throws Exception {
        when(bidListService.getBidList(anyInt())).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/update/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list?error"));
    }

    @Test
    public void update_bidList_ok() throws Exception {
        BidList bid = new BidList("Account Test", "Type Test", 10d);
        when(bidListService.updateBidList(any(BidList.class))).thenReturn(bid);
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                .flashAttr("bidList", bid))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }

    @Test
    public void update_bidList_error_type_blank() throws Exception {
        BidList bid = new BidList("Account Test", "", 10d);
        mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                .flashAttr("bidList", bid))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("bidList", "type"))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    public void delete_bidList_ok() throws Exception {
        doNothing().when(bidListService).deleteBidList(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("/bidList/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/bidList/list"));
    }
}
