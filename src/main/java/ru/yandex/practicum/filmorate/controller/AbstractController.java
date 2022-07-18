package ru.yandex.practicum.filmorate.controller;

import  ru.yandex.practicum.filmorate.model.*;
import java.util.List;

public abstract  class AbstractController <T extends AbstactModel> {

public abstract T create(T obj);

public abstract T update(T obj);

public abstract List<T> getAllRecords();

abstract  void validateObj(T obj);
}
