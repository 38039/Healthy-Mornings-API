package com.nforge.healthymorningsapi.controller;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.Level;
import com.nforge.healthymorningsapi.service.LevelService;


@RestController
@RequestMapping("/levels")
public class LevelController {
    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
        System.out.println("HM-API: (LevelController) Inicjalizacja kontrolera poziomów");
    }


    @GetMapping("/")
    public ResponseEntity<Level> getUserLevel() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser.getLevel());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Level>> getLevels() {
        List<Level> levels = levelService.getAllLevels();

        return ResponseEntity.ok(levels);
    }
}
