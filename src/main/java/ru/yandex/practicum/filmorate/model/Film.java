package ru.yandex.practicum.filmorate.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Film extends AbstactModel {

    Long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
}
