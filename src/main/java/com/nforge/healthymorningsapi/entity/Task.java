package com.nforge.healthymorningsapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnore // WAŻNE: Nie chcemy by zadania zwracały przypisanych użytkowników + bez tego JSON może się zapętlić
    @ManyToMany(mappedBy = "assignedTasks") @Fetch(FetchMode.JOIN)
    private Set<User> users = new HashSet<>();
}
