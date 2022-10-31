package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

public interface MpaDao {

    //Получить рейтинг
    Mpa getMpa(Integer id);

    //Получить список жанров
    Collection<Mpa> getAll();
}
