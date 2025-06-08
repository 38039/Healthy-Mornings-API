package com.nforge.healthymorningsapi.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.Task;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<List<Task>> findByUsers     (User user);
    Optional<Task>       findByUsersAndId (User user, Long id);
}
