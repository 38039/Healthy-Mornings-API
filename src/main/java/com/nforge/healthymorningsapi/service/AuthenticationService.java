package com.nforge.healthymorningsapi.service;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.nforge.healthymorningsapi.model.User;
import com.nforge.healthymorningsapi.repository.UserRepository;
import com.nforge.healthymorningsapi.payload.AuthorizationRequest;
import com.nforge.healthymorningsapi.payload.RegistrationRequest;

@Service
public class AuthenticationService {
    private final UserRepository        userRepository;
//    private final PasswordEncoder       passwordEncoder; TODO: BCRYPT
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository         = userRepository;
//        this.passwordEncoder        = passwordEncoder; BCRYPT
        this.authenticationManager  = authenticationManager;
        System.out.println("[!] HM-API: (AuthenticationService) Inicjalizacja serwisu obsługi uwierzytelniania użytkownika");
    }

    public User signup(RegistrationRequest request) {
//        User user = new User()
//                .setNickname(request.getNickname())
//                .setEmail(request.getEmail())
//                .setPassword(passwordEncoder.encode(request.getPassword()))
//                .setDateOfBirth(request.getDateOfBirth());

        User user = new User();
        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setDateOfBirth(request.getDateOfBirth());

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
