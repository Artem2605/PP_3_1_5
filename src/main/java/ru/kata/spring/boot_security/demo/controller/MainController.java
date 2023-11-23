package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MainController {

    private final UserServiceImpl USER_SERVICE_IMPL;
    private final RoleServiceImpl ROLE_SERVICE_IMPL;

    @Autowired
    public MainController(UserServiceImpl userServiceImpl, RoleServiceImpl roleServiceImpl) {
        this.USER_SERVICE_IMPL = userServiceImpl;
        this.ROLE_SERVICE_IMPL = roleServiceImpl;
    }

    @GetMapping("/")
    public String goHomePage() {
        return "home";
    }

    @PostMapping("/registry") //Первичная регистрация admin
    public String createAdminUser() {
        Role role1 = new Role("ROLE_ADMIN");
        Role role2 = new Role("ROLE_USER");
        List<Role> roles = new ArrayList<>() {
            {
                add(role1);
                add(role2);
            }
        };
        ROLE_SERVICE_IMPL.saveRole(role1);
        ROLE_SERVICE_IMPL.saveRole(role2);
        User user = new User("admin", "admin", "admin", Long.valueOf("777"), roles);
        USER_SERVICE_IMPL.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String goAdminPageListOfUsers(Model model) {
        model.addAttribute("allUsers", USER_SERVICE_IMPL.getListOfUsers());
        return "allUsers";
    }

    @GetMapping("/admin/new")
    public String getFormForCreateUser(@ModelAttribute("user") User user, Model model) {
        user.setRoles(Collections.singletonList(new Role("ROLE_USER")));
        model.addAttribute("user", user);
        return "new";
    }

    @PostMapping("/admin/addUser")
    public String createUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "new";
        } else {
            ROLE_SERVICE_IMPL.saveRole(user.getRoles().get(0));
            if (!USER_SERVICE_IMPL.saveUser(user)) {
                FieldError usernameError = new FieldError("user", "username",
                        "Данное имя занято");
                bindingResult.addError(usernameError);
                return "new";
            }
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/update")
    public String getUserForUpdate(@RequestParam(value = "id") Long id,
                                   Model model) {
        User tempUser = USER_SERVICE_IMPL.getUserById(id);
        tempUser.setPassword(""); //сбрасываем пароль для повторного encod-инга
        model.addAttribute("user", tempUser);
        return "update";
    }

    @PostMapping("/admin/updateUser")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "update";
        } else {
            ROLE_SERVICE_IMPL.saveRole(user.getRoles().get(0));
            USER_SERVICE_IMPL.updateUser(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("/admin/delete")
    public String getUserForDelete(@RequestParam(value = "id") Long id,
                                   Model model) {
        model.addAttribute("user", USER_SERVICE_IMPL.getUserById(id));
        return "delete";
    }

    @PostMapping("/admin/deleteUser")
    public String deleteUser(@ModelAttribute("user") User user) {
        USER_SERVICE_IMPL.deleteUserById(user.getId());
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String goUserPage(Principal principal, Model model) {
        model.addAttribute("user", USER_SERVICE_IMPL.findByUsername(principal.getName()));
        return "user";
    }
}