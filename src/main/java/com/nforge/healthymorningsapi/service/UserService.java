// Obsługuje logikę użytkowników
package com.nforge.healthymorningsapi.service;

import java.util.List;
import java.util.Date;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.BadCredentialsException;
import com.nforge.healthymorningsapi.entity.User;
import com.nforge.healthymorningsapi.repository.UserRepository;
import com.nforge.healthymorningsapi.payload.EditProfileRequest;
import com.nforge.healthymorningsapi.payload.EditPasswordRequest;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        System.out.println("[!] HM-API: (UserService) Inicjalizacja serwisu użytkowników");
    }


    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User updateUser(User user, EditProfileRequest request) {
        // Ból
        if (request.getName()     != null) user.setName(     request.getName()     );
        if (request.getSurname()  != null) user.setSurname(  request.getSurname()  );
        if (request.getGender()   != null) user.setGender(   request.getGender()   );
        if (request.getNickname() != null) user.setNickname( request.getNickname() );
        if (request.getEmail()    != null) user.setEmail(    request.getEmail()    );
        if (request.getBio()      != null) user.setBio(      request.getBio()      );
        if (request.getHeight()   != null) user.setHeight(   request.getHeight()   );
        if (request.getWeight()   != null) user.setWeight(   request.getWeight()   );

        userRepository.save(user);
        return user;
    }

    public User updatePassword(User user, EditPasswordRequest request) {
        // Aktualnie użytkownik nie podaje starego hasła przy prośbie zmiany na nowe,
        // ale potem można to zaimplementować, i wtedy to odkomentować
//        if (!passwordEncoder.matches(
//                request.getCurrentPassword(),
//                user.getPassword()
//            )
//        ) throw new BadCredentialsException("Użytkownik podał błędne stare hasło");

        // Chcemy by nadpisywane w bazie hasło było różne niż te podane przez użytkownika (by nie nadpisywać niepotrzebne danych)
        // lepiej by API to obsługiwało gdyż żądanie może niekoniecznie pochodzić od klienta
        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword()
        ) ) throw new BadCredentialsException("Użytkownik próbuje nadpisywać te samo hasło");

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        // Chcemy by po zapisie dane użytkownika były na nowo pobierane, gdyż REST zwraca użytkownika
        Optional<User> savedUser = userRepository.findByIdUser(user.getIdUser());

        return savedUser.orElse(null);
    }

    public void deleteUser(User user) {
//        Gdybyśmy chcieli by użytkownik podał hasło przed usunięciem konta
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Niepoprawne hasło");
//        }

        userRepository.delete(user);
    }

    // Zwraca czy użytkownik istnieje na podstawie interesującego nas atrybutu i jego nazwy
    public boolean doesUserExist(String type, Object input) {
        try {
            return switch (type) {                              // Rzutowanie typów z object
                // Nie zwraca danych personalnych, tylko status istnienia, zatem nie widzę powodu, by komentować ten kod
                case "ID"           -> this.findByID(           (Long  ) input) != null;
                case "name"         -> this.findByName(         (String) input) != null;
                case "surname"      -> this.findBySurname(      (String) input) != null;
                case "dateOfBirth"  -> this.findByDateOfBirth(  (Date  ) input) != null;
                case "gender"       -> this.findByGender(       (String) input) != null;
                case "height"       -> this.findByHeight(       (Float)  input) != null;
                case "weight"       -> this.findByWeight(       (Float)  input) != null;
                case "nickname"     -> this.findByNickname(     (String) input) != null;
                case "email"        -> this.findByEmail(        (String) input) != null;
                default -> false;
            };
        } catch (Exception e) {
            return false; // Proszę mnie nie zabijać
        }

    }

    // Skoro już mamy metodę, która zwraca status istnienia użytkownika na podstawie jego danych, miejmy też taką samą, która zwraca samego użytkownika
    public User returnOnArgument(String type, Object input) {
        try {
            return switch (type) {                              // Rzutowanie typów z object
                case "ID"           -> this.findByID(           (Long  ) input);
                // Lepiej używać tylko tych pól których wartości muszą być unikatowe, żeby nie zwrócić randomowych użytkowników
//            case "name"         -> this.findByName(         (String) input);
//            case "surname"      -> this.findBySurname(      (String) input);
//            case "dateOfBirth"  -> this.findByDateOfBirth(  (Date  ) input);
//            case "gender"       -> this.findByGender(       (String) input);
//            case "height"       -> this.findByHeight(       (Float)  input);
//            case "weight"       -> this.findByWeight(       (Float)  input);
                case "nickname"     -> this.findByNickname(     (String) input);
                case "email"        -> this.findByEmail(        (String) input);
                default -> null;
            };
        } catch (Exception e) {
            return null;
        }

    }

    public User findByID(Long ID) {
        return userRepository.findByIdUser(ID)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie istnieje w bazie danych"));
    }

    public User findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
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