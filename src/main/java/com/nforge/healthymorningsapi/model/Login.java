package com.nforge.healthymorningsapi.model;

import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity @NoArgsConstructor @AllArgsConstructor @Builder @Getter @Setter
@Table(name = "user_logins", schema = "application")
public class Login {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_login")
    private Long idLogin;

    @JoinColumn(name = "id_user")
    @NotNull @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "login_time")
    @NotNull
    private LocalDateTime loginTime;

}
