package com.nforge.healthymorningsapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "levels", schema = "application")
public class Level {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Column(name = "id_level")
    private Integer id;

    @Column(name = "name", unique = true)
    @NotBlank
    private String name;

    @Getter @Column(name = "description")
    private String description;

    @Getter @Column(name = "level_up_message")
    private String levelUpMessage;

    @Getter @Column(name = "minimum_points")
    @NotNull @Min(0) // Wartość musi być pozytywna
    private Long minimumPoints;

    @Getter @Column(name = "maximum_points")
    @NotNull @Min(0)
    private Long maximumPoints;

    @Getter @Column(name = "reward_bonus")
    @Min(0)
    private Long rewardBonus;

}
