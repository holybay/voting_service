package by.it_academy.jd2.golubev_107.voting_service.util;

import java.util.Collections;

public class Util {

    public static final int MIN_PARAMS_COUNT = 1;

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
