package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    String login;
    String name;
    LocalDate birthday;
}
