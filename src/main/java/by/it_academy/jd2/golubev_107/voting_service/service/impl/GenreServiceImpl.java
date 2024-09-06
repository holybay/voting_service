package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.service.IGenreService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.GenreCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.GenreOutDto;
import by.it_academy.jd2.golubev_107.voting_service.storage.IGenreStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.GenreStorageDbImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenreServiceImpl implements IGenreService {

    private static final IGenreService INSTANCE = new GenreServiceImpl();
    private final IGenreStorage genreStorage = GenreStorageDbImpl.getInstance();

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
