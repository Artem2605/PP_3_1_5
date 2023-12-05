package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class MainRestController {

    private final UserServiceImpl userService;

    @Autowired
    public MainRestController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getListOfUsers() {
        return (userService.getListOfUsers().isEmpty()) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userService.getListOfUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        return (userService.getUserById(id) == null) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid User user,
                                        BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.saveUser(user);
            return new ResponseEntity<>(userService.findByUsername(user.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody @Valid User user,
                                        BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            userService.updateUser(user);
            return new ResponseEntity<>(userService.findByUsername(user.getUsername()), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id) != null) {
            userService.deleteUserById(id);
        }
    }
}