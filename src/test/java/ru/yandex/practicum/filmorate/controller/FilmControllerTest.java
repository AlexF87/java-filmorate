package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.Exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void validateNameEmpty() throws Exception {
        Film film = new Film();
        film.setName("");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(2001, 01, 10));
        film.setDuration(10);
        MvcResult mvcResult = mockMvc.perform(post("/films")
                .contentType("application/Json")
                .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void validateMaxLengthDescription() throws Exception {
        Film film = new Film();
        film.setName("ok");
        film.setDescription("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль." +
                " Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги," +
                "а именно 20 миллионов. о Куглов, который за время «своего отсутствия», " +
                "стал кандидатом Коломбани.");
        film.setReleaseDate(LocalDate.of(2001, 01, 10));
        film.setDuration(10);
        MvcResult mvcResult = mockMvc.perform(post("/films")
                        .contentType("application/Json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void validateFailReleaseDate() {
        FilmController filmController = new FilmController();
        Film film = new Film();
        film.setName("ok");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        film.setDuration(10);
        assertThrows(ValidationException.class, () -> filmController.validateObj(film));
    }

    @Test
    void validateDuration() throws Exception {
        Film film = new Film();
        film.setName("ok");
        film.setDescription("asd");
        film.setReleaseDate(LocalDate.of(1995, 12, 27));
        film.setDuration(-10);
        MvcResult mvcResult = mockMvc.perform(post("/films")
                        .contentType("application/Json")
                        .content(objectMapper.writeValueAsString(film)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}