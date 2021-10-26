package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameControllerTest {

    @Mock
    private RuleNameService ruleNameService;

    @Spy
    @InjectMocks
    private RuleNameController ruleNameController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(ruleNameController).build();
    }

    @Test
    public void show_ruleName_list() throws Exception {
        when(ruleNameService.getAllRuleName()).thenReturn(List.of(new RuleName()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/list"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKeys("ruleNames", "ruleName");
    }

    @Test
    public void show_add_ruleName_form() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void save_ruleName_and_redirect_ruleName_list() throws Exception {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        when(ruleNameService.addRuleName(any(RuleName.class))).thenReturn(rule);
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                .flashAttr("ruleName", rule))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void save_ruleName_form_error() throws Exception {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part test 125 char testtttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt and tesssssssst t");
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/validate")
                .flashAttr("ruleName", rule))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("ruleName", "sqlPart"))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    public void show_update_ruleName_form() throws Exception {
        RuleName rule = new RuleName();
        when(ruleNameService.getRuleName(anyInt())).thenReturn(rule);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ruleName/update"))
                .andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("ruleName", rule);
    }

    @Test
    public void update_ruleName_ok() throws Exception {
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        when(ruleNameService.updateRuleName(any(RuleName.class))).thenReturn(rule);
        mockMvc.perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                .flashAttr("ruleName", rule))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/ruleName/list"));
    }

    @Test
    public void delete_ruleName_ok() throws Exception {
        doNothing().when(ruleNameService).deleteRuleName(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("/ruleName/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/ruleName/list"));
    }
}
