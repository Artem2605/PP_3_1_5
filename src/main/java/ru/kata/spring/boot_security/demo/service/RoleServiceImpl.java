package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository ROLE_REPOSITORY;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.ROLE_REPOSITORY = roleRepository;
    }

    @Override
    @Transactional
    public List<Role> getListOfRoles() {
        return ROLE_REPOSITORY.findAll();
    }

    @Override
    @Transactional
    public Role getRoleById(Long id) {
        return ROLE_REPOSITORY.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        ROLE_REPOSITORY.save(role);
    }

    @Override
    @Transactional
    public void updateRole(Role role) {
        ROLE_REPOSITORY.save(role);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        ROLE_REPOSITORY.deleteById(id);
    }
}