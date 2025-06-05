package com.nforge.healthymorningsapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;


@Getter @Setter
public class EditProfileRequest {

    @Size(max = 50)
    private String name;

    @Size(max = 50)
    private String surname;

    @Pattern(regexp = "male|female|other")
    private String gender;

    @Size(max = 30) @NotBlank
    private String nickname;

    @Size(max = 254) @NotBlank @Email(message = "Proszę podać poprawny adres email")
    private String email;

    private String bio;

    private Float weight;

    private Float height;

}