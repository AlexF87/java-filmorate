package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstactModel {

    Long id;
    @Email
    String email;
    String login;
    String name;
    LocalDate birthday;
}
