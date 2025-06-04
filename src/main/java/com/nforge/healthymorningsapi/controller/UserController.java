// Służy do zdefiniowania endpointów protokołu HTTP,
// za pomocą których klient będzie komunikował żądania do API
package com.nforge.healthymorningsapi.controller;

import com.nforge.healthymorningsapi.payload.RegistrationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.payload.AuthorizationRequest;
import com.nforge.healthymorningsapi.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("[!] HM-API: (UserController) Inicjalizacja kontrolera użytkowników");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthorizationRequest authorizationRequest) {

        // Najpierw weryfikowanym jest czy dane przesyłane przez klienta zgadzają się z danymi przechowywanymi w bazie danych
        boolean authenticationStatus = userService.authenticateUser(
                authorizationRequest.getEmail(),
                authorizationRequest.getPassword()
        );


        // Następnie zwracana jest odpowiedź w zależności od statusu
        if (authenticationStatus) {
            User user = userService.findByEmail(authorizationRequest.getEmail());
            return ResponseEntity.ok(user.getIdUser());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR 401: Podano nieprawidłowy email lub hasło!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationRequest registrationRequest) {
        boolean registerStatus = userService.registerUser(
                registrationRequest.getNickname(),
                registrationRequest.getEmail(),
                registrationRequest.getPassword(),   // TODO: [!] hasło ma być hashowane przez BCrypt
                registrationRequest.getDateOfBirth()
        );

        if (registerStatus) {
            return    ResponseEntity.status(HttpStatus.CREATED).body("Rejestracja zakończona sukcesem.");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik już istnieje.");
    }
}
