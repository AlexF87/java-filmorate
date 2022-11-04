package ru.yandex.practicum.filmorate.dao.impl;

import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmsGenresDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmsGenres;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class FilmsGenresDaoImpl implements FilmsGenresDao {

    private final JdbcTemplate jdbcTemplate;

    public FilmsGenresDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    //Получить все жанры заданных фильмов
    @Override
    public List<Film> getGenresForGivenIds(String idFilms, Map<Long, Film> films) {
        String sql = "SELECT g.genre_id, g.name_genre, fg.film_id " +
                "FROM genres AS g " +
                "RIGHT JOIN films_genres AS fg ON g.genre_id = fg.genres_id " +
                "WHERE fg.film_id IN ( " + idFilms + " );";
        List<FilmsGenres> filmsGenres = jdbcTemplate.query(sql, (rs, rowNum) -> makeGenres(rs, rowNum)
                );
        for (FilmsGenres filmsGenre : filmsGenres) {
             films.get(filmsGenre.getIdFilm()).getGenres().add(new Genre(filmsGenre.getGenreId(),
                     filmsGenre.getName()));
         }
        List <Film> filmList = new ArrayList<>();
        for(Map.Entry<Long, Film> entry : films.entrySet()) {
            filmList.add(entry.getValue());
        }
        return filmList;
    }

    private FilmsGenres makeGenres(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmsGenres.builder()
                .idFilm(resultSet.getInt("film_id"))
                .genreId(resultSet.getInt("genre_id"))
                .name(resultSet.getString("name_genre"))
                .build();
    }
}
