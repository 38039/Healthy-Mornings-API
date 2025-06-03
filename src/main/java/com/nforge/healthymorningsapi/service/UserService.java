// UserService odpowiada za logikę autoryzacji danych użytkownika
// podanych przez klienta, a tych znajdujących się w bazie danych
package com.nforge.healthymorningsapi.service;

import org.springframework.stereotype.Service;

import com.nforge.healthymorningsapi.model.User;
import com.nforge.healthymorningsapi.repository.UserRepository;


@Service
public class UserService {
    private final UserRepository userRepository;


    // Inicjalizacja repozytorium od komunikacji z bazą
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("Inicjalizacja komponentu UserService");
    }

    // Klient przekazuje do API podane email i hasło od użytkownika, następnie metoda:
    public boolean authenticateUser(String email, String password) {

        // TODO: [!] BCRYPT NIE JEST JESZCZE ZAIMPLEMENTOWANY
        //       [!] ODKOMENTOWAĆ TO PO IMPLEMENTACJI / PODMIENIĆ TEN HACK
//        // Wyszukuje użytkownika po emailu w bazie danych poprzez repozytorium
//        Optional<User> downloadedUserData = userRepository.findByEmail(email);
//        if (downloadedUserData.isEmpty()) return false; // Użytkownik musi istnieć w bazie
//
//        User user = downloadedUserData.get();   // Następnie następuje konwersja na lokalny rekord/model (ORM)
//        String hash = user.getPassword();       // Z którego za pomocą gettera wygenerowanego przez lombok wyciągany jest hash
//
//        // Ponieważ BCrypt generuje unikalny hash hash za każdym razem, wymagane jest użycie metod tej biblioteki do weryfikacji hasła
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        return encoder.matches(password, hash); // Zwraca true jeżeli hasła są takie same

        return userRepository.findByEmailAndPassword(email, password).isPresent();
    }

//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
//    }

}