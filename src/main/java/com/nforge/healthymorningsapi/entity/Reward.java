package com.nforge.healthymorningsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "rewards", schema = "application")
public class Reward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reward")
    @NotNull
    private Integer id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @Column(name = "description")
    @NotBlank
    private String description;

    @Column(name = "required_level")
    @NotNull
    private Long requiredLevel;

}
