package by.it_academy.jd2.golubev_107.voting_service.storage.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.IArtistStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArtistStorageMemoryImpl implements IArtistStorage {

    private static final IArtistStorage INSTANCE = new ArtistStorageMemoryImpl();
    private final Map<Long, Artist> storage = new HashMap<>();
    private long id;

    {
        List<Artist> initArtists = memoryArtistInit();
        for (Artist artist : initArtists) {
            storage.put(artist.getId(), artist);
        }
    }

    public static IArtistStorage getInstance() {
        return INSTANCE;
    }

    private ArtistStorageMemoryImpl() {
    }

    private List<Artist> memoryArtistInit() {
        String[] artistNames = {"RIHANNA", "DAFT PUNK", "AC DC", "JIMMY SAX"};
        List<Artist> toInit = new ArrayList<>();
        for (String artistName : artistNames) {
            Artist artist = new Artist();
            artist.setId(id++);
            artist.setName(artistName);
            toInit.add(artist);
        }
        return toInit;
    }

    @Override
    public Long create(Artist artist) {
        long id = this.id++;
        artist.setId(id);
        storage.put(id, artist);
        return id;
    }

    @Override
    public Artist readById(Long id) {
        return new Artist(storage.get(id));
    }

    @Override
    public List<Artist> readByName(String name) {
        List<Artist> out = new ArrayList<>();
        for (Artist artist : storage.values()) {
            if (artist.getName().equals(name)) {
                out.add(artist);
            }
        }
        return out;
    }

    @Override
    public List<Artist> readAll() {
        return List.copyOf(storage.values());
    }
}
