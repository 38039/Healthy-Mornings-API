package com.nforge.healthymorningsapi.service;

import com.nforge.healthymorningsapi.entity.Level;
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

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository         = userRepository;
        this.passwordEncoder        = passwordEncoder;
        this.authenticationManager  = authenticationManager;
        System.out.println("[!] HM-API: (AuthenticationService) Inicjalizacja serwisu obsługi uwierzytelniania użytkownika");
    }

    public User signup(RegistrationRequest request) {
//        User user = new User()
//                .setNickname(request.getNickname())
//                .setEmail(request.getEmail())
//                .setPassword(passwordEncoder.encode(request.getPassword()))
//                .setDateOfBirth(request.getDateOfBirth());

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

    public User authenticate(AuthorizationRequest input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow();
    }
}
