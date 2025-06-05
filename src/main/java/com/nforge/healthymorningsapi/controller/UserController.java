// Służy do zdefiniowania endpointów protokołu HTTP,
// za pomocą których klient będzie komunikował żądania do API
package com.nforge.healthymorningsapi.controller;

import java.util.List;

import com.nforge.healthymorningsapi.payload.EditProfileRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.service.UserService;


@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
        System.out.println("[!] HM-API: (UserController) Inicjalizacja kontrolera użytkowników");
    }


    @GetMapping("/profile")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping("/edit")
    public ResponseEntity<User> editUser(@RequestBody EditProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        currentUser = userService.updateUser(currentUser, request);

        return ResponseEntity.ok(currentUser);
    }
}
