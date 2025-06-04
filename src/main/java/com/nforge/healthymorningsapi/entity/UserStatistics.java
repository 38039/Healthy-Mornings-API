package com.nforge.healthymorningsapi.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "user_statistics", schema = "application")
public class UserStatistics {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_statistics")
    private Long idStatistics;

    @JoinColumn(name = "id_user", unique = true)
    @NotNull @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "tasks_active")
    @NotNull
    private short tasksActive;

    @Column(name = "tasks_completed")
    @NotNull
    private short tasksCompleted;
}
