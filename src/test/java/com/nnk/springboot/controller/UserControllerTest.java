package com.nnk.springboot.controller;

import com.nnk.springboot.controllers.UserController;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Before
    public void onInit() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void show_user_list() throws Exception {
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    @Test
    public void show_add_user_form() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    @Test
    public void save_user_and_redirect_user_list() throws Exception {
        UserDto userDto = new UserDto(1, "username", "123456B#", "fullname", "USER");
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .flashAttr("userDto", userDto))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void save_user_form_error() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPassword("12345678");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .flashAttr("userDto", userDto))
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("userDto", "username", "password", "fullName", "role"))
                .andExpect(view().name("user/add"));
    }

    @Test
    public void show_update_user_form() throws Exception {
        User user = new User();
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    @Test
    public void update_user_ok() throws Exception {
        UserDto userDto = new UserDto(1, "username", "123456B#", "fullname", "USER");
        when(userRepository.findAll()).thenReturn(List.of(new User()));
        when(userRepository.save(any(User.class))).thenReturn(new User());
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                .flashAttr("userDto", userDto))
                .andExpect(status().isFound())
                .andExpect(model().hasNoErrors())
                .andExpect(redirectedUrl("/user/list"));
    }

    @Test
    public void update_user_error() throws Exception {
        UserDto userDto = new UserDto(1, "", "password", "fullname", "USER");
        mockMvc.perform(MockMvcRequestBuilders.post("/user/update/1")
                .flashAttr("userDto", userDto))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(model().attributeHasFieldErrors("userDto", "username", "password"))
                .andExpect(view().name("user/update"));
    }

    @Test
    public void delete_user_ok() throws Exception {
        User user = new User();
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userRepository.findById(anyInt())).thenReturn(java.util.Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/user/delete/1"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/user/list"));

    }
}
