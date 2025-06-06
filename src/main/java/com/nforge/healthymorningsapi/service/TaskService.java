// Obsługuje logikę zadań
package com.nforge.healthymorningsapi.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nforge.healthymorningsapi.entity.UserTask;
import com.nforge.healthymorningsapi.payload.AddTaskRequest;
import com.nforge.healthymorningsapi.repository.UserTaskRepository;
import jakarta.transaction.Transactional;
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


    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        taskRepository.findAll().forEach(tasks::add);

        return tasks;
    }

    public List<Task> getAllUserTasks(User user) {
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

        // Po utworzeniu system powinien przypisywać zadanie osobie, która go utworzyła na potrzeby aplikacji
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

    // Tę funkcjonalność chcemy raczej przeznaczyć dla Administratorów
    @Transactional
    public void deleteTask(Long taskID) {
        Task task = taskRepository.findById(taskID)
                .orElseThrow(() -> new RuntimeException("Zadanie o podanym ID nie istnieje"));

        // Manualne rozłączanie task od użytkowników
        for (User user : task.getUsers())
            user.getAssignedTasks().remove(task);
        task.getUsers().clear(); // Opcjonalne czyszczenie atrybutu user dla task

        taskRepository.delete(task);
    }
}
