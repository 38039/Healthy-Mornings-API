package com.nforge.healthymorningsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "tasks", schema = "application")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Column(name = "id_task")
    private Integer id;

    @Column(name = "category")
    @NotBlank
    private String category;

    @Column(name = "name", unique = true)
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "points_reward")
    @NotNull @Min(0)
    private Integer pointsReward;
}
