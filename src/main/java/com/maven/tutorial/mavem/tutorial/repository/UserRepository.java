package com.maven.tutorial.mavem.tutorial.repository;

import com.maven.tutorial.mavem.tutorial.model.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
