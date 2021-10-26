package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveControllerTest {

    @Mock
    private CurvePointService curvePointService;

    @Spy
    @InjectMocks
    private CurveController curveController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(curveController).build();
    }

    @Test
    public void show_curvePoint_list_ok() throws Exception {
        when(curvePointService.getAllCurvePoint()).thenReturn(List.of(new CurvePoint()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/list"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKeys("curvePoints", "curvePoint");
    }

    @Test
    public void show_add_curvePoint_form() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void save_curvePoint_and_redirect_curvePoint_list() throws Exception {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        when(curvePointService.addCurvePoint(any(CurvePoint.class))).thenReturn(curvePoint);
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    public void save_curvePoint_form_error() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    public void show_update_curvePoint_form() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        when(curvePointService.getCurvePoint(anyInt())).thenReturn(curvePoint);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("curvePoint/update"))
                .andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("curvePoint", curvePoint);
    }

    @Test
    public void update_curvePoint_ok() throws Exception {
        CurvePoint curvePoint = new CurvePoint(10, 10d, 30d);
        when(curvePointService.updateCurvePoint(any())).thenReturn(curvePoint);
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }

    @Test
    public void update_curvePoint_error() throws Exception {
        CurvePoint curvePoint = new CurvePoint();
        mockMvc.perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                .flashAttr("curvePoint", curvePoint))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("curvePoint", "curveId"))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    public void delete_curvePoint_ok() throws Exception {
        doNothing().when(curvePointService).deleteCurvePoint(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("/curvePoint/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/curvePoint/list"));
    }
}
