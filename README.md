# java-filmorate

# Это репозиторий проекта "FilmoRate"

---
* Java 11 
* Lombok
* Spring Boot
* JDBC
* SQL
* H2
* Maven

Приложение **умеет*:*
1. Хранить Пользователей и Фильмы
2. Методы:
   * Создание объекта (фильм или пользователь)
   * Обновление объекта (фильм или пользователь)
   * Получение списка всех объектов (фильмы или пользователы)
   * Ставить и удалять лайк :thumbsup: фильму
   * Возвращать список популярных фильмов (рейтинг по лайкам)
   * Добавлять и удалять друзей пользователя
   * Выводит друзей пользователей 
   * Выводит общих друзей пользователя 
   * Валидация входных данных по разным критериям
3. ER - диаграмма
   ![ER diagramma](src/main/resources/ER.png?raw=true)
Пример запроса к базе:
```roomsql

SELECT * FROM users;
SELECT * FROM users WHERE id=1;
SELECT * FROM films;
SELECT * FROM films WHERE id = 1;
```
---
Запуск приложения
```Cmd 
1. mvn package
2. cd filmorate/target
3. java -jar filmorate-0.0.1-SNAPSHOT.jar
```