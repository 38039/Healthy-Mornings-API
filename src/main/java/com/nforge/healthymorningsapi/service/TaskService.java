// Obsługuje logikę zadań
package com.nforge.healthymorningsapi.service;
import java.time.LocalDateTime;
import java.util.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.nforge.healthymorningsapi.entity.Task;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.UserTask;
import com.nforge.healthymorningsapi.payload.AddTaskRequest;
import com.nforge.healthymorningsapi.repository.TaskRepository;
import com.nforge.healthymorningsapi.repository.UserTaskRepository;


@Service
public class TaskService {
    private final LevelService levelService;
    private final TaskRepository taskRepository;
    private final UserTaskRepository userTaskRepository;

    public TaskService(TaskRepository taskRepository, UserTaskRepository userTaskRepository, LevelService levelService) {
        this.levelService = levelService;
        this.taskRepository = taskRepository;
        this.userTaskRepository = userTaskRepository;
        System.out.println("[!] HM-API: (TaskService) Inicjalizacja serwisu zadań");
    }


    public Task getTask(long id) {
        return taskRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Nie odnaleziono zadania o podanym identyfikatorze"));
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskRepository.findAll());
    }

    public Task getUserTask(User user, Long id) {
        return taskRepository.findByUsersAndId(user, id)
                .orElseThrow(() -> new RuntimeException("Zadanie o podanej relacji nie istnieje"));
    }

    public List<Task> getAllUserTasks(User user) {
        return taskRepository.findByUsers(user)
                .orElseThrow(() -> new RuntimeException("Użytkownik o podanych danych nie istnieje"));
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
        return getAllUserTasks(user);
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

    @Transactional
    public void setTaskStatus(Task task, User user, String status) {
        UserTask userTask = userTaskRepository.findByTaskAndUser(task, user)
                .orElseThrow(() -> new RuntimeException("Task not assigned to user"));

        userTask.setStatus(status);

        // Przydałoby się jeszcze dodać updatedAt
        if(status.equals("completed")) {
            user.setPoints(user.getPoints() + task.getPointsReward());
            levelService.updateUserLevel(user);
            userTask.setCompletedAt(LocalDateTime.now());
        }

        userTaskRepository.save(userTask);
    }

    public UserTask getUserTaskRelation(User user, Task task) {
        return userTaskRepository.findByTaskAndUser(task, user)
                .orElseThrow(() -> new RuntimeException("Relacja między użytkownikiem a zadaniem nie istnieje"));
    }
}
