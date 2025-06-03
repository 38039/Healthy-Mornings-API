// UserService odpowiada za logikę autoryzacji danych użytkownika
// podanych przez klienta, a tych znajdujących się w bazie danych
package com.nforge.healthymorningsapi.service;

import com.nforge.healthymorningsapi.payload.RegisterRequest;
import org.springframework.stereotype.Service;

import com.nforge.healthymorningsapi.model.User;
import com.nforge.healthymorningsapi.repository.UserRepository;

import java.util.Date;


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

    public boolean registerUser(RegisterRequest request) {
        return userRepository.registerUserCredencials(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getDateOfBirth()
        );
    }

    // Zwraca czy użytkownik istnieje na podstawie interesującego nas atrybutu i jego nazwy
    public boolean doesUserExist(String type, Object input) {
        return switch (type) {                              // Rzutowanie typów z object
            // Nie zwraca danych personalnych, tylko status istnienia, zatem nie widzę powodu by komentować ten kod
            case "ID"           -> this.findByID(           (Long  ) input) != null;
            case "name"         -> this.findByName(         (String) input) != null;
            case "surname"      -> this.findBySurname(      (String) input) != null;
            case "dateOfBirth"  -> this.findByDateOfBirth(  (Date  ) input) != null;
            case "gender"       -> this.findByGender(       (String) input) != null;
            case "height"       -> this.findByHeight(       (Float)  input) != null;
            case "weight"       -> this.findByWeight(       (Float)  input) != null;
            case "username"     -> this.findByUsername(     (String) input) != null;
            case "email"        -> this.findByEmail(        (String) input) != null;
            default -> false;
        };
    }

    // Skoro już mamy metodę która zwraca status istnienia użytkownika na podstawie jego danych, miejmy też taką samą która zwraca samego użytkownika
    public User returnOnArgument(String type, Object input) {
        return switch (type) {                              // Rzutowanie typów z object
            case "ID"           -> this.findByID(           (Long  ) input);
            // Lepiej używać tylko tych pól których wartości muszą być unikatowe, żeby nie zwrócić randomowych użytkowników
//            case "name"         -> this.findByName(         (String) input);
//            case "surname"      -> this.findBySurname(      (String) input);
//            case "dateOfBirth"  -> this.findByDateOfBirth(  (Date  ) input);
//            case "gender"       -> this.findByGender(       (String) input);
//            case "height"       -> this.findByHeight(       (Float)  input);
//            case "weight"       -> this.findByWeight(       (Float)  input);
            case "username"     -> this.findByUsername(     (String) input);
            case "email"        -> this.findByEmail(        (String) input);
            default -> null;
        };
    }

    public User findByID(Long ID) {
        return userRepository.findByIdUser(ID)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }


    // TODO: Żeby miało to sens, metoda musi zwracać listę użytkowników (np. pod statystyki)
    //  a nie pierwszego lepszego użytkownika w bazie który akurat ma taką cechę
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findBySurname(String surname) {
        return userRepository.findBySurname(surname)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByDateOfBirth(Date dateOfBirth) {
        return userRepository.findByDateOfBirth(dateOfBirth)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByGender(String gender) {
        return userRepository.findByGender(gender)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByHeight(Float height) {
        return userRepository.findByHeight(height)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByWeight(Float weight) {
        return userRepository.findByWeight(weight)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

}