// Model danych przesyłanych do API z zapytaniem w formacie JSON,
// który przechowuje podane przez klienta dane do logowania
package com.nforge.healthymorningsapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class AuthorizationRequest {

    @NotBlank @Email(message = "Podany email powinien trzymać się struktury email@domain.country")
    private String email;

    @NotBlank(message = "Podane hasło nie może być puste")
    @Size(min = 6, max = 100, message = "Hasło musi mieć minimum 6 znaków")
    private String password;
}
