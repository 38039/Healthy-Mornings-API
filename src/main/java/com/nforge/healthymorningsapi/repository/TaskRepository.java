package com.nforge.healthymorningsapi.repository;

import com.nforge.healthymorningsapi.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.Task;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUsers(User user);
}
