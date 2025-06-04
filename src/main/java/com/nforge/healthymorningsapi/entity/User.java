package com.nforge.healthymorningsapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity @Data @NoArgsConstructor @AllArgsConstructor @Builder
@Table(name = "users", schema = "application")
public class User implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Column(name = "id_user")
    private Long idUser;

    @Getter @Column(name = "name")
    @Size(max = 50)
    private String name;

    @Getter @Column(name = "surname")
    @Size(max = 50)
    private String surname;

    @Getter @Column(name = "date_of_birth")
    @NotNull
    private Date dateOfBirth;

    @Getter @Column(name = "gender")
    @Pattern(regexp = "male|female|other")
    private String gender;

    @Getter @Column(name = "height")
    private Float height;

    @Getter @Column(name = "weight")
    private Float weight;

    @Getter @Column(name = "username", unique = true)
    @Size(max = 30)
    @NotBlank // NotBlank upewnia się nie tylko że zmienna nie wynosi null, ale także że nie pojawiają się w niej białe znaki pokroju spacji
    private String nickname;

    @Getter @Column(name = "email", unique = true)
    @Size(max = 254)
    @NotBlank
    @Email(message = "Proszę podać poprawny adres email") // Bardzo użyteczne, weryfikuje czy użytkownik rzeczywiście podał email
    private String email;

    @Getter @Column(name = "password")
    @NotBlank // Hasło nie może być puste, co prawda klient to weryfikuje, ale to dodatkowe zabezpieczenie
    private String password;

    @Getter @Column(name = "bio")
    private String bio;

    // Getter może w przyszłości się przydać, na przykład do panelu statystyk konta
    @Getter @Column(name = "created_at")
    @NotNull
    private ZonedDateTime createdAt;

    @Getter @Column(name = "avatar_url")
    @Size(max = 2048)
    private String avatarUrl;

    // Przydatne w momencie w którym chcielibyśmy wprowadzić funkcjonalność zmiany persmiji użytkowników bezpośrednio w kliencie
    @Getter @Column(name = "is_admin")
    @NotNull
    private Boolean isAdmin;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "level", referencedColumnName = "id_level", foreignKey = @ForeignKey(name = "users_level_fkey"))
    private Level level;


    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
    @Override public String  getUsername()             { return email; }
    @Override public boolean isAccountNonExpired()     { return true;  }
    @Override public boolean isAccountNonLocked()      { return true;  }
    @Override public boolean isCredentialsNonExpired() { return true;  }
    @Override public boolean isEnabled()               { return true;  }
}