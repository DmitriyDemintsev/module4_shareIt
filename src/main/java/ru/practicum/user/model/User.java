package ru.practicum.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class User {
    private Long id;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String name;
}
