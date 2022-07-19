package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    Long id;

    @NotBlank
    String name;

    @Size(max = 200)
    String description;

    LocalDate releaseDate;

    @Positive
    int duration;
}
