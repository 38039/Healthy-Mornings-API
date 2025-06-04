package com.nforge.healthymorningsapi.entity;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "user_rewards", schema = "application")
public class UserReward {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_possession")
    private Long idPossession;

    @JoinColumn(name = "id_user")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JoinColumn(name = "id_reward")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private Reward reward;

}
