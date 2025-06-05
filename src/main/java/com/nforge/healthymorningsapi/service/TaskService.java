// Obsługuje logikę zadań
package com.nforge.healthymorningsapi.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.nforge.healthymorningsapi.entity.Task;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.repository.TaskRepository;


@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        System.out.println("[!] HM-API: (TaskService) Inicjalizacja serwisu zadań");
    }


    public List<Task> getAllTasks(User currentUser) {
        return taskRepository.findByUsers(currentUser);
    }
}
