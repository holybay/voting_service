package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.IGenreService;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoFull;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VoteInptDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.vote.VotesResult;
import by.it_academy.jd2.golubev_107.voting_service.storage.IVoteStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.VoteStorageImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class VoteServiceImpl implements IVoteService {

    private static final IVoteService instance = new VoteServiceImpl();
    private final IVoteStorage voteStorage = VoteStorageImpl.getInstance();
    private final IArtistService artistService = ArtistServiceImpl.getInstance();
    private final IGenreService genreService = GenreServiceImpl.getInstance();

    private VoteServiceImpl() {
    }

    public static IVoteService getInstance() {
        return instance;
    }

    @Override
    public VotesResult calculate(VoteInptDto inDto) {
        validate(inDto);
        Vote voteToSave = toVote(inDto);
        voteStorage.save(voteToSave);

        Map<Artist, Integer> calculatedArtists = setupArtists(voteStorage.getArtistResults());
        Map<Genre, Integer> calculatedGenres = setupGenres(voteStorage.getGenreResults());
        List<Comment> commentResults = new ArrayList<>(voteStorage.getComments());

        Map<Artist, Integer> sortedCalcArtists = sortArtistsByVotes(calculatedArtists);
        Map<Genre, Integer> sortedCalcGenres = sortGenresByVotes(calculatedGenres);
        commentResults.sort(Comparator.comparing(Comment::getDateVoted).reversed());

        return new VotesResult(sortedCalcArtists, sortedCalcGenres, commentResults);
    }

    private Vote toVote(VoteInptDto inDto) {
        Vote toSave = new Vote();
        ArtistOutDto artistOutDto = artistService.getById(inDto.getArtist().getId());
        toSave.setArtist(toArtistEntity(artistOutDto));

        List<Genre> genreEntities = getGenreEntities(inDto.getGenres());
        toSave.setGenres(genreEntities);

        toSave.setComment(inDto.getComment());
        return toSave;
    }

    private List<Genre> getGenreEntities(List<GenreVotingDtoSimple> genreInDtos) {
        List<Genre> genres = new ArrayList<>();
        for (GenreVotingDtoSimple genreInDto : genreInDtos) {
            GenreOutDto genreOut = genreService.getById(genreInDto.getId());
            genres.add(toGenreEntity(genreOut));
        }
        return genres;
    }

    private Artist toArtistEntity(ArtistOutDto outDto) {
        Artist artist = new Artist();
        artist.setId(outDto.getId());
        artist.setName(outDto.getName());
        return artist;
    }

    private Artist toArtistEntity(ArtistVotingDtoFull artistVotingDto) {
        Artist artist = new Artist();
        artist.setId(artistVotingDto.getId());
        artist.setName(artistVotingDto.getName());
        return artist;
    }

    private Genre toGenreEntity(GenreOutDto outDto) {
        Genre genre = new Genre();
        genre.setId(outDto.getId());
        genre.setName(outDto.getName());
        return genre;
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

    private Map<Genre, Integer> sortGenresByVotes(Map<Genre, Integer> toSort) {
        Map<Genre, Integer> sortedMap = new TreeMap<>((a1, a2) -> {
            int compare = toSort.get(a2).compareTo(toSort.get(a1));
            if (compare != 0) {
                return compare;
            }
            return a1.getName().compareTo(a2.getName());
        });
        sortedMap.putAll(toSort);
        return sortedMap;
    }

    private Map<Artist, Integer> setupArtists(Map<Long, Integer> rawArtistsVotes) {
        if (rawArtistsVotes.isEmpty()) {
            throw new RuntimeException("Empty artist vote results!");
        }
        Map<Artist, Integer> artistVotes = new HashMap<>();
        rawArtistsVotes.forEach((key, value) -> {
            Artist artist = toArtistEntity(artistService.getById(key));
            artistVotes.put(artist, value);
        });
        return artistVotes;
    }

    private Map<Genre, Integer> setupGenres(Map<Long, Integer> rawGenreVotes) {
        if (rawGenreVotes.isEmpty()) {
            throw new RuntimeException("Empty artist vote results!");
        }
        Map<Genre, Integer> genreVotes = new HashMap<>();
        rawGenreVotes.forEach((key, value) -> {
            Genre genre = toGenreEntity(genreService.getById(key));
            genreVotes.put(genre, value);
        });
        return genreVotes;
    }

    private void validate(VoteInptDto inDto) {
        List<String> errors = new ArrayList<>();

        List<String> artistErrors = artistService.validate(inDto.getArtist());
        if (!artistErrors.isEmpty()) {
            errors.addAll(artistErrors);
        }

        List<GenreVotingDtoSimple> genreInDtos = inDto.getGenres();
        List<String> genreErrors = genreService.validate(genreInDtos);
        if (genreInDtos != null && genreInDtos.size() < 3) {
            genreErrors.add("You've chosen less than 3 genres!");
        }
        if (!genreErrors.isEmpty()) {
            errors.addAll(genreErrors);
        }

        if (inDto.getComment().getTextComment() == null
                || inDto.getComment().getDateVoted() == null) {
            errors.add("Incorrect comment: " + inDto.getComment());
        }

        if (!errors.isEmpty()) {
            throw new RuntimeException(String.join("\n", errors));
        }
    }
}
