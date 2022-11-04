package ru.yandex.practicum.filmorate.dao;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class UserDaoTest {

    private final UserDao userDao;
    private final FriendsDao friendsDao;

    static User user1;
    static User user2;

    @BeforeAll
    static void init() {
        user1 = User.builder()
                .email("maile@qwe.er")
                .login("admin")
                .name("Kto1")
                .birthday(LocalDate.of(2000, 1, 15))
                .build();

        user2 = User.builder()
                .email("yahoo@qwe.com")
                .login("Jho")
                .name("ggg")
                .birthday(LocalDate.of(2000, 1, 15))
                .build();
    }
    @BeforeEach
    void init2() {
        userDao.createUser(user1);
        userDao.createUser(user2);
    }
    @AfterEach
    void clear() {
        userDao.deleteUser(user1.getId());
        userDao.deleteUser(user2.getId());
    }

    @Test
    void testUpdateUser() {
        user1.setLogin("dog");
        userDao.updateUser(user1);
        Optional<User> userOptional = Optional.of(userDao.getUser(user1.getId()));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).hasFieldOrPropertyWithValue("login", "dog")
                );
    }

    @Test
    void testCheckUserInDB() {
        assertThat(userDao.checkUserInDB(user2.getId())).isTrue();
        userDao.deleteUsers();
        List<User> users = userDao.getAll();
        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    void testCreateAndDeleteFriend() {
        friendsDao.createFriend(user1.getId(), user2.getId());
        List<User> friends = friendsDao.getFriendsOfUser(user1.getId());
        assertThat(friends.size()).isEqualTo(1);
        friendsDao.deleteFriend(user1.getId(), user2.getId());
        friends = friendsDao.getFriendsOfUser(user1.getId());
        assertThat(friends.size()).isEqualTo(0);
    }

    @Test
    void testGetCommonsFriend() {
        User commonsFriend = User.builder()
                .email("read@rambler.te")
                .login("reader")
                .name("rick")
                .birthday(LocalDate.of(1948, 9, 01))
                .build();
        userDao.createUser(commonsFriend);
        friendsDao.createFriend(user1.getId(), user2.getId());
        friendsDao.createFriend(user1.getId(), commonsFriend.getId());
        friendsDao.createFriend(user2.getId(), user1.getId());
        friendsDao.createFriend(user2.getId(), commonsFriend.getId());
        friendsDao.getCommonsFriend(user1.getId(), user2.getId());
        Optional<List<User>> userOptional = Optional.of( friendsDao.getCommonsFriend(user1.getId(), user2.getId()));

        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user).size().isEqualTo(1)
                );
    }
}
