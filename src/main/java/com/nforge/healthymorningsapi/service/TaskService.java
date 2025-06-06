// Obsługuje logikę zadań
package com.nforge.healthymorningsapi.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nforge.healthymorningsapi.entity.UserTask;
import com.nforge.healthymorningsapi.payload.AddTaskRequest;
import com.nforge.healthymorningsapi.repository.UserTaskRepository;
import org.springframework.stereotype.Service;
import com.nforge.healthymorningsapi.entity.Task;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.repository.TaskRepository;


@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;

    public TaskService(TaskRepository taskRepository, UserTaskRepository userTaskRepository) {
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
        System.out.println("[!] HM-API: (TaskService) Inicjalizacja serwisu zadań");
    }


    public List<Task> getAllTasks(User user) {
        return taskRepository.findByUsers(user);
    }

    public List<Task> addTask(User user, AddTaskRequest request) {
        Set<User> userSet = new HashSet<>();
        userSet.add(user);

        Task task = Task.builder()
                .name(request.getName())
                .description(request.getDescription())
                .category(request.getCategory())
                .pointsReward(request.getPoints())
                .users(userSet)
                .build();

        taskRepository.save(task);
        assignTaskToUser(user, task);

        return taskRepository.findByUsers(user);
    }

    public void assignTaskToUser(User user, Task task) {
        UserTask userTask = UserTask.builder()
                .user(user)
                .task(task)
                .assignedAt(LocalDateTime.now())
                .status("pending")
                .build();

        userTaskRepository.save(userTask);
    }
}
