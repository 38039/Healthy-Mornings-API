package com.nforge.healthymorningsapi.service;

import com.nforge.healthymorningsapi.entity.Level;
import com.nforge.healthymorningsapi.entity.UserStatistics;
import com.nforge.healthymorningsapi.exception.ExistingUserException;
import com.nforge.healthymorningsapi.repository.UserStatisticsRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.repository.UserRepository;
import com.nforge.healthymorningsapi.payload.AuthorizationRequest;
import com.nforge.healthymorningsapi.payload.RegistrationRequest;

import java.time.ZonedDateTime;

@Service
public class AuthenticationService {
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserStatisticsRepository userStatisticsRepository;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserService userService,
            UserRepository userRepository,
            UserStatisticsRepository userStatisticsRepository
    ) {
        this.userRepository         = userRepository;
        this.passwordEncoder        = passwordEncoder;
        this.authenticationManager  = authenticationManager;
        this.userService            = userService;
        this.userStatisticsRepository = userStatisticsRepository;
        System.out.println("[!] HM-API: (AuthenticationService) Inicjalizacja serwisu obsługi uwierzytelniania użytkownika");
    }

    // Czy nie lepiej przenieść to do UserService?
    public User signup(RegistrationRequest request) throws ExistingUserException {

        // Ble, można spróbować to podmienić na .orElseThrow()
        if (    userService.doesUserExist("nickname", request.getNickname() )   ||
                userService.doesUserExist("email",    request.getEmail()    )     )
            throw new ExistingUserException("Użytkownik o podanych danych już istnieje");

        Level level = new Level();
        level.setId(1);

        User user = new User();
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setDateOfBirth(request.getDateOfBirth());
        user.setCreatedAt(ZonedDateTime.now());
        user.setIsAdmin(false);
        user.setLevel(level);

        return userRepository.save(user);
    }

    public void generateStatisticsEntry(User user) {
        UserStatistics stats = UserStatistics.builder()
                .user(user)
                .tasksActive((short) 0)
                .tasksCompleted((short) 0)
                .build();

        userStatisticsRepository.save(stats);
    }

    public User authenticate(AuthorizationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        return userRepository.findByEmail(request.getEmail())
                .orElseThrow();
    }
}
