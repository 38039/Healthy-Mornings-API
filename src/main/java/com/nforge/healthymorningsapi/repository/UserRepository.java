package com.nforge.healthymorningsapi.repository;

import com.nforge.healthymorningsapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    Optional<User> findById_user(int id);

}
