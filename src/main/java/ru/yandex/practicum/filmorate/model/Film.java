package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    Long id;

    @NotBlank
    String name;

    @Size(max = 200)
    String description;

    LocalDate releaseDate;

    @Positive
    int duration;
    @Positive
    private Integer rate;
    private List<Genre> genres;
    private Mpa mpa;

}
