package by.it_academy.jd2.golubev_107.voting_service.storage;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;

import java.util.List;
import java.util.Map;

public interface IVoteStorage {

    void save(Vote vote);

    Map<Long, Integer> getArtistResults();

    Map<Long, Integer> getGenreResults();

    List<? extends Comment> getComments();

}
