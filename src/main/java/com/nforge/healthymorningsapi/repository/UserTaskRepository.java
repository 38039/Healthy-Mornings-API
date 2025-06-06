package com.nforge.healthymorningsapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.nforge.healthymorningsapi.entity.UserTask;


@Repository
public interface UserTaskRepository extends JpaRepository<UserTask, Long> {}
