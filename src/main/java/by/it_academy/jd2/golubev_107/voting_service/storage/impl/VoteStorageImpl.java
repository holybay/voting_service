package by.it_academy.jd2.golubev_107.voting_service.storage.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.IVoteStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EArtist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteStorageImpl implements IVoteStorage {

    private static final IVoteStorage instance = new VoteStorageImpl();
    private final List<Vote> allVotes = new ArrayList<>();
    private final Map<EArtist, List<Vote>> artists = new HashMap<>();
    private final Map<EGenre, List<Vote>> genres = new HashMap<>();
    private final List<Comment> comments = new ArrayList<>();

    private VoteStorageImpl() {
    }

    public static IVoteStorage getInstance() {
        return instance;
    }

    @Override
    public void init() {
        for (EArtist artist : EArtist.values()) {
            artists.put(artist, new ArrayList<>(EArtist.values().length));
        }
        for (EGenre genre : EGenre.values()) {
            genres.put(genre, new ArrayList<>(EGenre.values().length));
        }
    }

    @Override
    public void save(Vote vote) {
        saveToStorages(vote);
    }

    private void saveToStorages(Vote vote) {
        allVotes.add(vote);
        comments.add(vote.getComment());
        artists.compute(vote.getArtistName(), (k, v) -> {
            v.add(vote);
            return v;
        });

        for (EGenre genre : vote.getGenres()) {
            genres.compute(genre, (k, v) -> {
                v.add(vote);
                return v;
            });
        }
    }

    @Override
    public Map<EArtist, List<Vote>> getArtists() {
        return Map.copyOf(artists);
    }

    @Override
    public Map<EGenre, List<Vote>> getGenres() {
        return Map.copyOf(genres);
    }

    @Override
    public List<Comment> getComments() {
        return List.copyOf(comments);
    }
}
