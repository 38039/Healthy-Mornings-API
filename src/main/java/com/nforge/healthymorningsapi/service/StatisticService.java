package com.nforge.healthymorningsapi.service;
import java.util.List;
import java.util.ArrayList;
import com.nforge.healthymorningsapi.repository.UserStatisticsRepository;
import com.nforge.healthymorningsapi.repository.UserTaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.UserStatistics;
import com.nforge.healthymorningsapi.repository.UserRepository;


@Service
public class StatisticService {
    private final UserRepository userRepository;
    private final UserTaskRepository userTaskRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    public StatisticService(
            UserRepository userRepository,
            UserTaskRepository userTaskRepository,
            UserStatisticsRepository userStatisticsRepository
    ) {
        this.userRepository = userRepository;
        this.userTaskRepository = userTaskRepository;
        this.userStatisticsRepository = userStatisticsRepository;
        System.out.println("[!] HM-API: (TaskService) Inicjalizacja serwisu zadań");
    }


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void updateAllUserStatistics() {
//        List<User> users = allUsers();

        for (User user : allUsers() ) {
            UserStatistics stats = userStatisticsRepository.findByUser(user)
                    .orElseThrow(() -> new RuntimeException("Użytkownik o podanych danych nie ma przypisanych statystyk, prawdopodobnie nie istnieje"));

            short active    = userTaskRepository.countUserTaskByUser(user);
            short completed = userTaskRepository.countUserTaskByUserAndStatus(user, "completed");

            stats.setTasksActive(active);
            stats.setTasksCompleted(completed);

            userStatisticsRepository.save(stats);
        }
    }


    public UserStatistics getStatisticsForUser(User user) {
        return userStatisticsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Nie odnaleziono statystyk dla aktualnego użytkownika"));
    }
}
