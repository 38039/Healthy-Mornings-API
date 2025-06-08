package com.nforge.healthymorningsapi.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.UserStatistics;
import com.nforge.healthymorningsapi.service.StatisticService;


@RestController
@RequestMapping("/statistics")
public class StatisticController {
    private final StatisticService statisticService;

    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
        System.out.println("[!] HM-API: (UserStatisticsRepository) Inicjalizacja kontrolera statystyk");
    }

    @GetMapping("/")
    public ResponseEntity<UserStatistics> getUserStatistics() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        statisticService.updateAllUserStatistics();
        UserStatistics stats = statisticService.getStatisticsForUser(currentUser);
        return ResponseEntity.ok(stats);
    }
}
