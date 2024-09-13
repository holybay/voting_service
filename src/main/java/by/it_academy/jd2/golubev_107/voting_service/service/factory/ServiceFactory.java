package by.it_academy.jd2.golubev_107.voting_service.service.factory;

import by.it_academy.jd2.golubev_107.voting_service.service.IArtistService;
import by.it_academy.jd2.golubev_107.voting_service.service.IGenreService;
import by.it_academy.jd2.golubev_107.voting_service.service.IVoteService;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.ArtistServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.GenreServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.service.impl.VoteServiceImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.factory.StorageFactory;

public class ServiceFactory {

    private static final StorageFactory storageFactory = StorageFactory.getInstance();
    private static final ServiceFactory INSTANCE = new ServiceFactory();
    private final IArtistService artistService;
    private final IGenreService genreService;
    private final IVoteService voteService;


    private ServiceFactory() {
        artistService = new ArtistServiceImpl(storageFactory.getArtistStorage());
        genreService = new GenreServiceImpl(storageFactory.getGenreStorage());
        voteService = new VoteServiceImpl(storageFactory.getVoteStorage(), this.artistService, this.genreService);
    }

    public static ServiceFactory getInstance() {
        return INSTANCE;
    }

    public IArtistService getArtistService() {
        return artistService;
    }

    public IGenreService getGenreService() {
        return genreService;
    }

    public IVoteService getVoteService() {
        return voteService;
    }
}
