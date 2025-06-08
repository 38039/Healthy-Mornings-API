package com.nforge.healthymorningsapi.repository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.Task;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.UserTask;


@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    Optional<UserTask> findByTaskAndUser(Task task, User user);

    short countUserTaskByUser(User user);
    short countUserTaskByUserAndStatus(User user, String status);
}
