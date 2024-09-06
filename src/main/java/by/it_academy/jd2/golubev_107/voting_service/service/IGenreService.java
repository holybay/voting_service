package by.it_academy.jd2.golubev_107.voting_service.service;

import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreOutDto;

import java.util.List;

public interface IGenreService {

    void create(GenreCreateDto inDto);

    GenreOutDto getById(Long id);

    List<GenreOutDto> getAll();

//    List<String> validate(ArtistVotingDtoSimple artistVotingDtoSimple);
}
