package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.repository.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.repository.entity.EArtist;
import by.it_academy.jd2.golubev_107.voting_service.repository.entity.EGenre;
import by.it_academy.jd2.golubev_107.voting_service.repository.entity.Vote;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VoteServiceImpl implements IVoteService {

    private static final IVoteService instance = new VoteServiceImpl();
    private final List<Vote> allVotes = new ArrayList<>();
    private final Map<EArtist, List<Vote>> artists = new HashMap<>();
    private final Map<EGenre, List<Vote>> genres = new HashMap<>();
    private final List<Comment> comments = new ArrayList<>();


    private VoteServiceImpl() {
    }

    public static IVoteService getInstance() {
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
    public VotesResult calculate(VoteInptDto inDto) {
        validate(inDto);
        Vote voteToSave = toVote(inDto);
        saveToStorages(voteToSave);

        Map<EArtist, Integer> calculatedArtists = calculateArtists(artists);
        Map<EGenre, Integer> calculatedGenres = calculateGenres(genres);
        List<Comment> commentResults = new ArrayList<>(comments);

        Map<EArtist, Integer> sortedCalcArtists = sortArtistsByVotes(calculatedArtists);
        Map<EGenre, Integer> sortedCalcGenres = sortGenresByVotes(calculatedGenres);
        commentResults.sort(Comparator.comparing(Comment::getDateVoted).reversed());

        return new VotesResult(sortedCalcArtists, sortedCalcGenres, commentResults);
    }

    private Vote toVote(VoteInptDto inDto) {
        Vote toSave = new Vote();
        toSave.setArtistName(EArtist.valueOf(inDto.getArtistName()));
        toSave.setGenres(Util.toEGenresList(inDto.getGenres()));
        toSave.setComment(inDto.getComment());
        return toSave;
    }

    private Map<EArtist, Integer> sortArtistsByVotes(Map<EArtist, Integer> toSort) {
        Map<EArtist, Integer> sortedMap = new TreeMap<>((a1, a2) -> {
            int compare = toSort.get(a2).compareTo(toSort.get(a1));
            if (compare != 0) {
                return compare;
            }
            return a1.name().compareTo(a2.name());
        });
//        Map<EArtist, Integer> sortedMap = new TreeMap<>(Comparator.comparing(toSort::get).reversed());
        sortedMap.putAll(toSort);
        return sortedMap;
    }

    private Map<EGenre, Integer> sortGenresByVotes(Map<EGenre, Integer> toSort) {
        Map<EGenre, Integer> sortedMap = new TreeMap<>((a1, a2) -> {
            int compare = toSort.get(a2).compareTo(toSort.get(a1));
            if (compare != 0) {
                return compare;
            }
            return a1.name().compareTo(a2.name());
        });
        sortedMap.putAll(toSort);
        return sortedMap;
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

    private Map<EArtist, Integer> calculateArtists(Map<EArtist, List<Vote>> storage) {
        Map<EArtist, Integer> calculated = new HashMap<>();
        for (Map.Entry<EArtist, List<Vote>> entry : storage.entrySet()) {
            calculated.put(entry.getKey(), entry.getValue().size());
        }
        return calculated;
    }

    private Map<EGenre, Integer> calculateGenres(Map<EGenre, List<Vote>> storage) {
        Map<EGenre, Integer> calculated = new HashMap<>();
        for (Map.Entry<EGenre, List<Vote>> entry : storage.entrySet()) {
            calculated.put(entry.getKey(), entry.getValue().size());
        }
        return calculated;
    }

    private void validate(VoteInptDto inDto) {
        List<String> errors = new ArrayList<>();
        enumCheck(EArtist.class, inDto.getArtistName(), errors, "ARTIST");

        int genresCounter = 0;
        for (String genre : inDto.getGenres()) {
            enumCheck(EGenre.class, genre, errors, "GENRE");
            genresCounter++;
        }
        if (genresCounter < 3) {
            errors.add("You've chosen less than 3 genres: " + Arrays.toString(inDto.getGenres()));
        }

        if (inDto.getComment().getTextComment() == null
                || inDto.getComment().getDateVoted() == null) {
            errors.add("Incorrect comment: " + inDto.getComment());
        }

        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join("\n", errors));
        }
    }

    private <T extends Enum<T>> void enumCheck(Class<T> enumClazz,
                                               String value, List<String> errors, String message) {
        if (value == null) {
            errors.add(String.format("%s can't be blank: %s", message, value));
            return;
        }
        try {
            Enum.valueOf(enumClazz, value);
        } catch (IllegalArgumentException e) {
            errors.add(String.format("Wrong %s name: : %s", message, value));
        }
    }
}
