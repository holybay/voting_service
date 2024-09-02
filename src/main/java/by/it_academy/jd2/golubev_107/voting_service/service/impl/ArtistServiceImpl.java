package by.it_academy.jd2.golubev_107.voting_service.service.impl;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistCreateDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistOutDto;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.ArtistVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.storage.IArtistStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.ArtistStorageDbImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtistServiceImpl implements IArtistService {

    private static final IArtistService INSTANCE = new ArtistServiceImpl();
    private final IArtistStorage artistStorage = ArtistStorageDbImpl.getInstance();

    private ArtistServiceImpl() {
    }

    public static IArtistService getInstance() {
        return INSTANCE;
    }

    @Override
    public void create(ArtistCreateDto inDto) {
        validate(inDto);
        Artist entity = toEntity(inDto);
        artistStorage.create(entity);
    }

    @Override
    public ArtistOutDto getById(Long id) {
        return toOutDto(artistStorage.readById(id));
    }

    @Override
    public List<ArtistOutDto> getAll() {
        List<Artist> artists = artistStorage.readAll();
        if (artists.isEmpty()) {
            return Collections.emptyList();
        }
        List<ArtistOutDto> artistsOut = new ArrayList<>();
        for (Artist artist : artists) {
            artistsOut.add(toOutDto(artist));
        }
        return artistsOut;
    }

    @Override
    public List<String> validate(ArtistVotingDtoSimple dto) {
        List<String> errors = new ArrayList<>();
        Long id = dto.getId();
        if (id == null) {
            errors.add("Vote form has been changed! Such artist doesn't exist!");
        }
        if (id != null && artistStorage.readById(id) == null) {
            errors.add("Vote form has been changed! Such artist doesn't exist!");
        }
        return errors;
    }

    private void validate(ArtistCreateDto inDto) {
        List<String> errors = new ArrayList<>();
        String artistName = inDto.getName();
        if (artistName == null) {
            errors.add("Artist name can't be blank!");
        }
        if (artistName != null && !artistStorage.readByName(artistName).isEmpty()) {
            errors.add("Artist with the such name already exists!");
        }
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }
    }

    private ArtistOutDto toOutDto(Artist artist) {
        ArtistOutDto outDto = new ArtistOutDto();
        outDto.setId(artist.getId());
        outDto.setName(artist.getName());
        return outDto;
    }

    private Artist toEntity(ArtistCreateDto inDto) {
        Artist artist = new Artist();
        artist.setName(inDto.getName());
        return artist;
    }
}
