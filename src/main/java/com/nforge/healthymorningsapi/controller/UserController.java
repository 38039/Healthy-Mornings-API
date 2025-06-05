// Służy do zdefiniowania endpointów protokołu HTTP,
// za pomocą których klient będzie komunikował żądania do API
package com.nforge.healthymorningsapi.controller;

import java.util.List;

import com.nforge.healthymorningsapi.payload.EditPasswordRequest;
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


    @GetMapping("/get/profile")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/get/profile-all")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @PutMapping("/edit/profile")
    public ResponseEntity<User> editUser(@RequestBody EditProfileRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        currentUser = userService.updateUser(currentUser, request);

        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/edit/password")
    public ResponseEntity<User> editPassword(@RequestBody EditPasswordRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        currentUser = userService.updatePassword(currentUser, request);

        return ResponseEntity.ok(currentUser);
    }

    @DeleteMapping("/delete/profile")
    public ResponseEntity<?> deleteUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        userService.deleteUser(currentUser);
        return ResponseEntity.ok("Konto zostało pomyślnie usunięte!");
    }
}
