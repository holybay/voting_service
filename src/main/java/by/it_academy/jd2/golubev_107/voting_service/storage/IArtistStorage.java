package by.it_academy.jd2.golubev_107.voting_service.storage;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;

import java.util.List;

public interface IArtistStorage {

    Long create(Artist artist);

    Artist readById(Long id);

    List<Artist> readByName(String name);

    List<Artist> readAll();
}
