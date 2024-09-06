package by.it_academy.jd2.golubev_107.voting_service.storage;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;

import java.util.List;

public interface IGenreStorage {

    Long create(Genre genre);

    Genre readById(Long id);

    Genre readByName(String name);

    List<Genre> readAll();
}
