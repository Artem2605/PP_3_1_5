package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import java.util.List;

public interface RoleService {

    List<Role> getListOfRoles();

    Role getRoleById(Long id);

    void saveRole(Role role);

    void updateRole(Role role);

    void deleteUserById(Long id);
}