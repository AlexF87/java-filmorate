package ru.yandex.practicum.filmorate.controller;


import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Validated
public abstract  class AbstractController <T> {

public abstract T create(@Valid T obj);

public abstract T update(@Valid T obj);

public abstract List<T> getAllRecords();

}
