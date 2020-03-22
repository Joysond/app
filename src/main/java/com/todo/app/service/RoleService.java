package com.todo.app.service;

import com.todo.app.others.UserRole;
import com.todo.app.models.Role;
import com.todo.app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repository;

    public Optional<Role> findByName(UserRole name) {
        return repository.findByName(name);
    }
}
