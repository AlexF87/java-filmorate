package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    //Получить все рейтинги
    @GetMapping
     public Collection<Mpa> getAllMpa() {
        return mpaService.getAllMpa();
    }

    //Получить по id рейтинг
    @GetMapping("/{id}")
    public Mpa get(@PathVariable int id) {
        return mpaService.getById(id);
    }
}
