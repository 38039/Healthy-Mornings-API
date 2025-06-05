package com.nforge.healthymorningsapi.repository;

import java.util.Date;
import java.util.Optional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdUser(@NotNull Long idUser);
    Optional<User> findByName(@NotNull String name);
    Optional<User> findBySurname(@NotNull String surname);
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(@NotNull String username); // Podmiana z username, żeby nie kolidować z Spring

    // Dosłownie 1984
    Optional<User> findByDateOfBirth(@NotNull Date dateOfBirth);
    Optional<User> findByGender(@NotNull String gender);
    Optional<User> findByHeight(@NotNull Float height);
    Optional<User> findByWeight(@NotNull Float weight);
}
