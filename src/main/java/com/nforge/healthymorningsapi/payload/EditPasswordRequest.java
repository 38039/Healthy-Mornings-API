package com.nforge.healthymorningsapi.payload;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter @Setter
public class EditPasswordRequest {
    @NotBlank private String newPassword;
}
