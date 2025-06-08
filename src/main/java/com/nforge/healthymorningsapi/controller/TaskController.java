// Obsługuje żądania protokołu HTTP związane z zadaniami
package com.nforge.healthymorningsapi.controller;

import java.util.List;

import com.nforge.healthymorningsapi.entity.UserTask;
import com.nforge.healthymorningsapi.payload.AddTaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<Task> getUserTask(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Task task = taskService.getUserTask(currentUser, id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/")
    public ResponseEntity<List<Task>> getUserTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        List<Task> tasks = taskService.getAllUserTasks(currentUser);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping("/")
    public ResponseEntity<List<Task>> addTask(@RequestBody AddTaskRequest addTaskRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        List<Task> tasks = taskService.addTask(currentUser, addTaskRequest);
        return ResponseEntity.ok(tasks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        List<Task> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<UserTask> markTaskAsDone(@PathVariable Long id, @PathVariable String status) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Task currentTask = taskService.getTask(id);

        taskService.setTaskStatus(currentTask, currentUser, status);
        UserTask overwrittenRelation = taskService.getUserTaskRelation(currentUser, currentTask);

        return ResponseEntity.ok(overwrittenRelation);
    }

}