package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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
public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @Spy
    @InjectMocks
    private RatingController ratingController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    public void show_rating_list() throws Exception {
        when(ratingService.getAllRating()).thenReturn(List.of(new Rating()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/rating/list"))
                .andExpect(status().isOk()).andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsKeys("ratings", "rating");
    }

    @Test
    public void show_add_rating() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/add"));
    }

    @Test
    public void save_rating_and_redirect_rating_list() throws Exception {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        when(ratingService.addRating(any(Rating.class))).thenReturn(rating);
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/validate")
                .flashAttr("rating", rating))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void show_update_rating_form() throws Exception {
        Rating rating = new Rating();
        when(ratingService.getRating(anyInt())).thenReturn(rating);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/rating/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("rating/update"))
                .andReturn();
        Map<String, Object> model = Objects.requireNonNull(mvcResult.getModelAndView()).getModel();
        assertThat(model).containsEntry("rating", rating);
    }

    @Test
    public void update_rating_ok() throws Exception {
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        when(ratingService.updateRating(any(Rating.class))).thenReturn(rating);
        mockMvc.perform(MockMvcRequestBuilders.post("/rating/update/1")
                .flashAttr("rating", rating))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/rating/list"));
    }

    @Test
    public void delete_rating_ok() throws Exception {
      doNothing().when(ratingService).deleteRating(anyInt());
        mockMvc.perform(MockMvcRequestBuilders.get("/rating/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/rating/list"));
    }
}
