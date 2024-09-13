package by.it_academy.jd2.golubev_107.voting_service.service;

import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoSimple;

import java.util.List;

public interface IArtistService {

    void create(ArtistCreateDto inDto);

    ArtistOutDto getById(Long id);

    List<ArtistOutDto> getAll();

    List<String> validate(ArtistVotingDtoSimple artistVotingDtoSimple);
}
