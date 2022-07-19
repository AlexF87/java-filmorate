package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    Long id;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String login;
    String name;

    @Past
    LocalDate birthday;
}
