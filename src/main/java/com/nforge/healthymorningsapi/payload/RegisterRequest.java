// Model danych przesyłanych do API z zapytaniem w formacie JSON,
// który przechowuje podane przez klienta dane do rejestracji
package com.nforge.healthymorningsapi.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String nickname;

    @NotBlank @Email(message = "Podany email powinien trzymać się struktury email@domain.country")
    private String email;

    @NotBlank @Size(min = 6, max = 100, message = "Hasło musi mieć minimum 6 znaków")
    private String password;

    // JsonFormat rzutuje datę z stringa w JSONie na java.util.Date
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull
    private Date dateOfBirth;
}
