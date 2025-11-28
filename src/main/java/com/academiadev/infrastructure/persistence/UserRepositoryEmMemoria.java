package com.academiadev.infrastructure.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.academiadev.application.repositories.UserRepository;
import com.academiadev.domain.entities.User;

public class UserRepositoryEmMemoria implements UserRepository {
    private final Map<String, User> db = new HashMap<>(); 

    @Override
    public void save(User user) {
        db.put(user.getEmail(), user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(db.get(email));
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(db.values());
    }
}