package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.dto.UserDto;
import com.nnk.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestMapping("/user/list")
    public String home(Model model)
    {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(UserDto userDto) {
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserDto userDto, BindingResult result, Model model) {
        if (!result.hasErrors()) {
            User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getFullName(), userDto.getRole());
            if (userRepository.findUserByUsername(user.getUsername()) == null){
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(user.getPassword()));
                userRepository.save(user);
                model.addAttribute("users", userRepository.findAll());
                return "redirect:/user/list";
            }
            model.addAttribute("error", "An account for that username already exists.");
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        UserDto userDto = new UserDto(id, user.getUsername(), "", user.getFullName(), user.getRole());
        model.addAttribute("userDto", userDto);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDto userDto,
                             BindingResult result, Model model) {
        if (!result.hasErrors()) {
            User existingUser = userRepository.findUserByUsername(userDto.getUsername());
            if (existingUser == null || (id.equals(existingUser.getId()))){
                User user = new User(id, userDto.getUsername(), "",  userDto.getFullName(), userDto.getRole());
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                user.setPassword(encoder.encode(userDto.getPassword()));
                userRepository.save(user);
                model.addAttribute("users", userRepository.findAll());
                return "redirect:/user/list";
            }
            model.addAttribute("error", "An account for that username already exists.");
        }

        return "user/update";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "redirect:/user/list";
    }
}
