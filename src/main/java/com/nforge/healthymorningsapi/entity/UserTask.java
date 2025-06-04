package com.nforge.healthymorningsapi.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "user_tasks", schema = "application")
public class UserTask {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_assignment")
    private Long idAssignment;

    @JoinColumn(name = "id_user")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "id_task")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Column(name = "assigned_at")
    @NotNull
    private LocalDateTime assignedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @NotNull
    private TaskStatus status;

}

// Enum for task_status
enum TaskStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    SKIPPED
}

