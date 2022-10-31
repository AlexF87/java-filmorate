package ru.yandex.practicum.filmorate.dao;


import ru.yandex.practicum.filmorate.model.Likes;

import java.util.Collection;

public interface LikesDao {

    //Добавить лайк
    void addLike(Long filmId, Long userId);

    //Получить список лайков данного фильма
    Collection<Likes> getAllLikes(Long filmId);

    //Удалить лайк пользователя у заданного фильма
    void deleteLike(Long filmId, Long userId);
}
