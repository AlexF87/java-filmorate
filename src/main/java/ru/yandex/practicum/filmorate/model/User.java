package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User extends AbstactModel {

    Long id;
    String email;
    String login;
    String name;
    LocalDate birthday;
}
