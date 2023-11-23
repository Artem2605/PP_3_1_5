package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;
import java.util.List;

public interface UserService {

    User findByUsername(String username);

    List<User> getListOfUsers();

    User getUserById(Long id);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserById(Long id);
}