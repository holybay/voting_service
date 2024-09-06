package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoFull;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.storage.IVoteStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.VoteStorageImpl;
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
    private final IVoteStorage voteStorage = VoteStorageImpl.getInstance();
    private final IArtistService artistService = ArtistServiceImpl.getInstance();

    private VoteServiceImpl() {
    }

    public static IVoteService getInstance() {
        return instance;
    }

    @Override
    public void init(List<ArtistVotingDtoFull> artistDtos) {
        if (artistDtos.isEmpty()) {
            throw new RuntimeException("There are not artists to vote for!");
        }
        List<Artist> artists = new ArrayList<>();
        for (ArtistVotingDtoFull dto : artistDtos) {
            artists.add(toArtistEntity(dto));
        }
        voteStorage.init(artists);
    }

    @Override
    public VotesResult calculate(VoteInptDto inDto) {
        validate(inDto);
        Vote voteToSave = toVote(inDto);
        voteStorage.save(voteToSave);

        Map<Artist, Integer> calculatedArtists = calculateArtists(voteStorage.getArtists());
        Map<EGenre, Integer> calculatedGenres = calculateGenres(voteStorage.getGenres());
        List<Comment> commentResults = new ArrayList<>(voteStorage.getComments());

        Map<Artist, Integer> sortedCalcArtists = sortArtistsByVotes(calculatedArtists);
        Map<EGenre, Integer> sortedCalcGenres = sortGenresByVotes(calculatedGenres);
        commentResults.sort(Comparator.comparing(Comment::getDateVoted).reversed());

        return new VotesResult(sortedCalcArtists, sortedCalcGenres, commentResults);
    }

    private Vote toVote(VoteInptDto inDto) {
        Vote toSave = new Vote();
        ArtistOutDto artistOutDto = artistService.getById(inDto.getArtist().getId());
        toSave.setArtist(toArtistEntity(artistOutDto));
        toSave.setGenres(Util.toEGenresList(inDto.getGenres()));
        toSave.setComment(inDto.getComment());
        return toSave;
    }

    private Artist toArtistEntity(ArtistVotingDtoFull artistVotingDto) {
        Artist artist = new Artist();
        artist.setId(artistVotingDto.getId());
        artist.setName(artistVotingDto.getName());
        return artist;
    }

    private Artist toArtistEntity(ArtistOutDto outDto) {
        Artist artist = new Artist();
        artist.setId(outDto.getId());
        artist.setName(outDto.getName());
        return artist;
    }

    private Map<Artist, Integer> sortArtistsByVotes(Map<Artist, Integer> toSort) {
        Map<Artist, Integer> sortedMap = new TreeMap<>((a1, a2) -> {
            int compare = toSort.get(a2).compareTo(toSort.get(a1));
            if (compare != 0) {
                return compare;
            }
            return a1.getName().compareTo(a2.getName());
        });
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

    private Map<Artist, Integer> calculateArtists(Map<Artist, List<Vote>> storage) {
        Map<Artist, Integer> calculated = new HashMap<>();
        for (Map.Entry<Artist, List<Vote>> entry : storage.entrySet()) {
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

        List<String> artistErrors = artistService.validate(inDto.getArtist());
        if (!artistErrors.isEmpty()) {
            errors.addAll(artistErrors);
        }

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
