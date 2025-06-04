// Służy do zdefiniowania endpointów protokołu HTTP,
// za pomocą których klient będzie komunikował żądania do API
package com.nforge.healthymorningsapi.controller;

import com.nforge.healthymorningsapi.payload.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nforge.healthymorningsapi.model.User;
import com.nforge.healthymorningsapi.payload.LoginRequest;
import com.nforge.healthymorningsapi.service.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("Inicjalizacja komponentu UserController");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        // Najpierw weryfikowanym jest czy dane przesyłane przez klienta zgadzają się z danymi przechowywanymi w bazie danych
        boolean authenticationStatus = userService.authenticateUser(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );


        // Następnie zwracana jest odpowiedź w zależności od statusu
        if (authenticationStatus) {
            User user = userService.findByEmail(loginRequest.getEmail());
            return ResponseEntity.ok(user.getIdUser());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("ERROR 401: Podano nieprawidłowy email lub hasło!");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        boolean registerStatus = userService.registerUser(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                registerRequest.getPassword(),   // TODO: [!] hasło ma być hashowane przez BCrypt
                registerRequest.getDateOfBirth()
        );

        if (registerStatus) {
            return    ResponseEntity.status(HttpStatus.CREATED).body("Rejestracja zakończona sukcesem.");
        } else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik już istnieje.");
    }
}
