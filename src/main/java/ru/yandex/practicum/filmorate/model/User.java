package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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

    @JsonIgnore
    Set<Long> friends = new HashSet<>();
}
