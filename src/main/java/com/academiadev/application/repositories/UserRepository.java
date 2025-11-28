package com.academiadev.application.repositories;

import com.academiadev.domain.entities.User;
import java.util.Optional;
import java.util.List;

public interface UserRepository {
    void save(User user);
    Optional<User> findByEmail(String email);
    List<User> findAll();
}