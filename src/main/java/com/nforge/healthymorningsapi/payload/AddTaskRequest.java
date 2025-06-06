package com.nforge.healthymorningsapi.payload;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Getter @Setter
public class AddTaskRequest {
    @NotBlank String name;
    @NotBlank String description;
    @NotBlank String category;
    @NotNull int points;
}
