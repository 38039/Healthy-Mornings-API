// Klasa AppConfig służy do konfiguracji Springa
package com.nforge.healthymorningsapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.nforge.healthymorningsapi.repository.UserRepository;


@Configuration
@ComponentScan(basePackages = "com.nforge.healthymorningsapi") // Spring ma przeszukać wszystkie podpakiety pakietu głównego
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("[!] HM-API: (ApplicationConfiguration) Załadowano konfigurację aplikacji");
    }

    @Bean // Zwraca email, ale pod nazwą username, mylące, ale tak musi być
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie odnaleziono użytkownika o adresie: " + username));
    }

    @Bean // Metoda ta tworzy i zwraca instancję kodera służącego do szyfrowania haseł
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean // Potrzebne do uwierzytelniania podczas zapytania
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}
