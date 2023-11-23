package ru.kata.spring.boot_security.demo.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DbInit {

    private final UserServiceImpl USER_SERVICE_IMPL;
    private final RoleServiceImpl ROLE_SERVICE_IMPL;

    @Autowired
    public DbInit(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.USER_SERVICE_IMPL = userService;
        this.ROLE_SERVICE_IMPL = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Role roleOfAdmin = new Role("ROLE_ADMIN");
        Role roleOfUser = new Role("ROLE_USER");
        List<Role> listOfRoles = new ArrayList<>() {
            {
                add(roleOfAdmin);
                add(roleOfUser);
            }
        };
        ROLE_SERVICE_IMPL.saveRole(roleOfAdmin);
        ROLE_SERVICE_IMPL.saveRole(roleOfUser);
        User user1 = new User("admin", "admin", "admin",
                Long.valueOf("777"), listOfRoles);
        USER_SERVICE_IMPL.saveUser(user1);
        User user2 = new User("ivan", "ivanov", "ivan",
                Long.valueOf("2000"), listOfRoles);
        USER_SERVICE_IMPL.saveUser(user2);
        User user3 = new User("vasya", "vasilyev", "vasya",
                Long.valueOf("1995"), Collections.singletonList(roleOfUser));
        USER_SERVICE_IMPL.saveUser(user3);
    }
}