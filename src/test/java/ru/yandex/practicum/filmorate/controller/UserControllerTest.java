package ru.yandex.practicum.filmorate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;
    @MockBean
    private UserStorage userStorage;
    @Test
    void validateFailEmail() throws Exception {
        User user = new User();
        user.setEmail("error");
        user.setLogin("ok");
        user.setName("qwe");
        user.setBirthday(LocalDate.of(1999, 01, 01));

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType("application/Json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void validateEmptyLogin() throws Exception {
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("");
        user.setName("qwe");
        user.setBirthday(LocalDate.of(1999, 01, 01));

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType("application/Json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }

    @Test
    void validateEmptyName() {
        UserService userService = new UserService(userStorage);
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("AAA");
        user.setName("");
        user.setBirthday(LocalDate.of(1999, 01, 01));
        userService.validateObj(user);

        assertEquals("AAA", user.getName());
    }

    @Test
    void validateFutureBirthday() throws Exception {
        User user = new User();
        user.setEmail("asd@asd.ru");
        user.setLogin("sdf");
        user.setName("qwe");
        user.setBirthday(LocalDate.now().plusDays(1));

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .contentType("application/Json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andReturn();
        assertEquals(400, mvcResult.getResponse().getStatus());
    }
}