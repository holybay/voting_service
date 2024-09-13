package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.service.IGenreService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.storage.IGenreStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;
import by.it_academy.jd2.golubev_107.voting_service.storage.factory.StorageFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class GenreServiceImpl implements IGenreService {

    private static final IGenreService INSTANCE = new GenreServiceImpl();
    private final IGenreStorage genreStorage = StorageFactory.getInstance().getGenreStorage();

    private GenreServiceImpl() {
    }

    public static IGenreService getInstance() {
        return INSTANCE;
    }

    @Override
    public void create(GenreCreateDto inDto) {
        validate(inDto);
        Long id = genreStorage.create((toEntity(inDto)));
    }

    @Override
    public GenreOutDto getById(Long id) {
        Genre genre = genreStorage.readById(id);
        return toOutDto(genre);
    }

    @Override
    public List<GenreOutDto> getAll() {
        List<Genre> genres = genreStorage.readAll();
        if (genres.isEmpty()) {
            return Collections.emptyList();
        }
        List<GenreOutDto> outDtos = new ArrayList<>();
        for (Genre genre : genres) {
            outDtos.add(toOutDto(genre));
        }
        return outDtos;
    }

    @Override
    public List<String> validate(List<GenreVotingDtoSimple> dtoToValidate) {
        List<Long> dtoIds = dtoToValidate.stream()
                                         .map(GenreVotingDtoSimple::getId)
                                         .toList();

        Set<Long> fromDb = genreStorage.readAllByIds(dtoIds).stream()
                                       .map(Genre::getId)
                                       .collect(Collectors.toSet());

        List<String> validErrs = new ArrayList<>();
        if (fromDb.isEmpty()) {
            validErrs.add(String.format("Incorrect genre ids provided: %s", dtoIds));
        }

        dtoIds.forEach(e -> {
            if (!fromDb.contains(e)) {
                validErrs.add("Incorrect genre id provided: " + e);
            }
        });
        return validErrs;
    }

    private void validate(GenreCreateDto inDto) {
        List<String> errors = new ArrayList<>();
        String genreName = inDto.getName();
        if (genreName == null) {
            errors.add("Genre name can't be blank!");
        }
        if (genreName != null && genreStorage.readByName(genreName) != null) {
            errors.add("Genre with the such name already exists!");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }
    }

    private Genre toEntity(GenreCreateDto dto) {
        Genre genre = new Genre();
        genre.setName(dto.getName());
        return genre;
    }

    private GenreOutDto toOutDto(Genre genre) {
        GenreOutDto dto = new GenreOutDto();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }
}
