package com.nforge.healthymorningsapi.repository;

import com.nforge.healthymorningsapi.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByIdUser(@NotNull Long idUser);
    Optional<User> findByName(@NotNull String name);
    Optional<User> findBySurname(@NotNull String surname);

    // Dosłownie 1984
    Optional<User> findByDateOfBirth(@NotNull Date dateOfBirth);
    Optional<User> findByGender(@NotNull String gender);
    Optional<User> findByHeight(@NotNull Float height);
    Optional<User> findByWeight(@NotNull Float weight);

    Optional<User> findByUsername(@NotNull String username);
    Optional<User> findByEmail(String email);
//    Optional<User> findByPassword(@NotNull String password); Hashowanie haseł?

    // Tymczasowe, gdyż nie ma implementacji BCrypta
    Optional<User> findByEmailAndPassword(String email, String password);
}
