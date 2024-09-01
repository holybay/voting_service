package by.it_academy.jd2.golubev_107.voting_service.service;

import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistInDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistOutDto;

import java.util.List;

public interface IArtistService {

    void create(ArtistInDto inDto);

    ArtistOutDto getById(Long id);

    List<ArtistOutDto> getAll();
}
