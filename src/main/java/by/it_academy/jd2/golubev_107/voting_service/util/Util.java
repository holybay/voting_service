package by.it_academy.jd2.golubev_107.voting_service.util;

import by.it_academy.jd2.golubev_107.voting_service.storage.entity.EGenre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Util {

    public static final int MIN_PARAMS_COUNT = 1;

    public static List<EGenre> toEGenresList(String[] genres) {
        List<EGenre> eGenres = new ArrayList<>(genres.length);
        for (String genre : genres) {
            eGenres.add(EGenre.valueOf(genre));
        }
        return eGenres;
    }

    public static String setDynamicSqlParams(String sql, final int paramsCount) {
        if (paramsCount < MIN_PARAMS_COUNT) {
            throw new IllegalArgumentException("SQL params amount can't be negative or equal 0");
        }
        if (paramsCount > MIN_PARAMS_COUNT) {
            final StringBuilder sb = new StringBuilder(
                    String.join(", ", Collections.nCopies(paramsCount, "?")));
            sql = sql.replace("(?)", "(" + sb + ")");
        }
        return sql;
    }

}
