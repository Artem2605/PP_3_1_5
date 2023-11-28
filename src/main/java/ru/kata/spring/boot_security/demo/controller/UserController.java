package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.security.Principal;

@Controller
public class UserController {

    private final UserService USER_SERVICE_IMPL;

    @Autowired
    public UserController(UserService userService) {
        this.USER_SERVICE_IMPL = userService;
    }

    @GetMapping("/user")
    public String goUserPage(Principal principal, Model model) {
        model.addAttribute("entryUser", USER_SERVICE_IMPL.findByUsername(principal.getName()));
        return "userPage";
    }
}