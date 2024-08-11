package by.it_academy.jd2.golubev_107.voting_service.storage;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EArtist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;

import java.util.List;
import java.util.Map;

public interface IVoteStorage {

    void init();

    void save(Vote vote);

    <K extends EArtist, V extends List<? extends Vote>> Map<K, V> getArtists();

    <K extends EGenre, V extends List<? extends Vote>> Map<K, V> getGenres();

    List<? extends Comment> getComments();

}
