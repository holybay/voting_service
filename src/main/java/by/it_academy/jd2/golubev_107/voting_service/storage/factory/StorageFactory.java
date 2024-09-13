package by.it_academy.jd2.golubev_107.voting_service.storage.factory;

import by.it_academy.jd2.golubev_107.voting_service.storage.IArtistStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.IGenreStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.IVoteStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl.ConnectionManagerImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.ArtistStorageDbImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.GenreStorageDbImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.impl.VoteStorageImpl;

public class StorageFactory {

    private static final StorageFactory INSTANCE = new StorageFactory();
    private final IArtistStorage artistStorage;
    private final IGenreStorage genreStorage;
    private final IVoteStorage voteStorage;

    private StorageFactory() {
        IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
        artistStorage = new ArtistStorageDbImpl(connectionManager);
        genreStorage = new GenreStorageDbImpl(connectionManager);
        voteStorage = new VoteStorageImpl(connectionManager);
    }

    public static StorageFactory getInstance() {
        return INSTANCE;
    }

    public IArtistStorage getArtistStorage() {
        return artistStorage;
    }

    public IGenreStorage getGenreStorage() {
        return genreStorage;
    }

    public IVoteStorage getVoteStorage() {
        return voteStorage;
    }
}
