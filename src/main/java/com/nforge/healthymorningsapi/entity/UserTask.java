package com.nforge.healthymorningsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Pattern;
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

    @JsonIgnore
    @JoinColumn(name = "id_user")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonIgnore
    @JoinColumn(name = "id_task")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Column(name = "assigned_at")
    @NotNull
    private LocalDateTime assignedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Pattern(regexp = "pending|in_progress|completed|skipped")
    @Column(name = "status")
    @NotNull
    private String status;
}

