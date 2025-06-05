// Obsługuje żądania protokołu HTTP związane z zadaniami
package com.nforge.healthymorningsapi.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nforge.healthymorningsapi.entity.Task;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.service.TaskService;


@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
        System.out.println("[!] HM-API: (TaskController) Inicjalizacja kontrolera zadań");
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Task>> getUserTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        List<Task> tasks = taskService.getAllTasks(currentUser);
        return ResponseEntity.ok(tasks);
    }
}

