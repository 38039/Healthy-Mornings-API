package com.nforge.healthymorningsapi.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.Level;


public interface LevelRepository extends JpaRepository<Level, Long> {
    List<Level> findAllByOrderByMinimumPointsAsc();
}
