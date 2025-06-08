// Obsługuje żądania protokołu HTTP związane z autoryzacją
package com.nforge.healthymorningsapi.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.service.JwtService;
import com.nforge.healthymorningsapi.service.AuthenticationService;
import com.nforge.healthymorningsapi.payload.AuthorizationRequest;
import com.nforge.healthymorningsapi.payload.AuthorizationResponce;
import com.nforge.healthymorningsapi.payload.RegistrationRequest;


@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService            jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService             = jwtService;
        this.authenticationService  = authenticationService;
        System.out.println("[!] HM-API: (AuthenticationController) Inicjalizacja kontrolera autoryzacji");
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegistrationRequest request) {
        User registeredUser = authenticationService.signup(request);
        authenticationService.generateStatisticsEntry(registeredUser);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthorizationResponce> authenticate(@RequestBody AuthorizationRequest request) {
        User authenticatedUser  = authenticationService.authenticate(request);
        String jwtToken         = jwtService.generateToken(authenticatedUser);

        AuthorizationResponce loginResponse = new AuthorizationResponce()
                .setToken(jwtToken)
                .setExpiresIn( jwtService.getExpirationTime() );

        return ResponseEntity.ok(loginResponse);
    }
}
