package by.it_academy.jd2.golubev_107.voting_service.util;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static List<EGenre> toEGenresList(String[] genres) {
        List<EGenre> eGenres = new ArrayList<>(genres.length);
        for (String genre : genres) {
            eGenres.add(EGenre.valueOf(genre));
        }
        return eGenres;
    }

}
