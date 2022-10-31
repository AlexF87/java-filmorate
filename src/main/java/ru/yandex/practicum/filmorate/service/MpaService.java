package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.IdNegativeException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.Collection;

@Service
public class MpaService {
    private final MpaDao mpaDao;

    @Autowired
    public MpaService(MpaDao mpaDao) {
        this.mpaDao = mpaDao;
    }

    //Получить рейтинг по id
    public Mpa getById(int id) {
        checkMpaId(id);
        return mpaDao.getMpa(id);
    }

    //Получить все рейтинги
    public Collection<Mpa> getAllMpa() {
        return  mpaDao.getAll();
    }

    //Проверка id
    private void checkMpaId(int id) {
        if (id < 0) {
            throw new IdNegativeException("id - must be positive and > 0");
        }
    }
}
