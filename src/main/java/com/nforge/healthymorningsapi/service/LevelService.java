package com.nforge.healthymorningsapi.service;
import java.util.List;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.entity.Level;
import com.nforge.healthymorningsapi.repository.LevelRepository;
import com.nforge.healthymorningsapi.repository.UserRepository;


@Service
public class LevelService {
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;

    public LevelService(UserRepository userRepository, LevelRepository levelRepository) {
        this.userRepository = userRepository;
        this.levelRepository = levelRepository;
        System.out.println("HM-API: (LevelService) Inicjalizacja serwisu poziomów");
    }


    @Transactional
    public void updateUserLevel(User user) {
        long userPoints = user.getPoints();
        List<Level> levels = levelRepository.findAllByOrderByMinimumPointsAsc();

        Level selectedLevel = null;
        for (Level level : levels) {
            if (userPoints >= level.getMinimumPoints()) {
                selectedLevel = level;
            } else break;
        }

        if (selectedLevel != null && !selectedLevel.equals(user.getLevel())) {
            user.setLevel(selectedLevel);
            userRepository.save(user);
        }
    }

    public List<Level> getAllLevels() {
        return levelRepository.findAll();
    }
}
