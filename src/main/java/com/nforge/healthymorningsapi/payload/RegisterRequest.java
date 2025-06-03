// Model danych przesyłanych do API z zapytaniem w formacie JSON,
// który przechowuje podane przez klienta dane do rejestracji
package com.nforge.healthymorningsapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter @Setter
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank @Email(message = "Podany email powinien trzymać się struktury email@domain.country")
    private String email;

    @NotBlank @Size(min = 6, max = 100, message = "Hasło musi mieć minimum 6 znaków")
    private String password;

    @NotNull
    private Date dateOfBirth;
}
