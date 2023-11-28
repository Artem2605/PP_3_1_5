package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AdminController {

    private final UserServiceImpl USER_SERVICE_IMPL;
    private final RoleServiceImpl ROLE_SERVICE_IMPL;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.USER_SERVICE_IMPL = userServiceImpl;
        this.ROLE_SERVICE_IMPL = roleServiceImpl;
    }

    @GetMapping("/admin")
    public String goAdminPageListOfUsers(Principal principal, Model model) {
        model.addAttribute("entryUser", USER_SERVICE_IMPL.findByUsername(principal.getName()));
        model.addAttribute("allUsers", USER_SERVICE_IMPL.getListOfUsers());
        model.addAttribute("allRoles", ROLE_SERVICE_IMPL.getListOfRoles());
        model.addAttribute("newUser", new User());
        return "adminPage";
    }

    @PostMapping("/admin/addUser")
    public String createUser(@ModelAttribute("newUser") @Valid User user,
                             BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            USER_SERVICE_IMPL.saveUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            USER_SERVICE_IMPL.updateUser(user);
        }
        return "redirect:/admin";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@RequestParam(value = "id") Long id) {
        USER_SERVICE_IMPL.deleteUserById(id);
        return "redirect:/admin";
    }
}