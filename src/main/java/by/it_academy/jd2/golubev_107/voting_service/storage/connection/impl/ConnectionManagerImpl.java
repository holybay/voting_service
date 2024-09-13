package by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements IConnectionManager {

    private static final String JDBC_DRIVER = "org.postgresql.Driver";
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/votes";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";
    private static final IConnectionManager INSTANCE = new ConnectionManagerImpl();
    private final DataSource dataSource;

    {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(JDBC_DRIVER);
            cpds.setJdbcUrl(DB_URL);
            cpds.setUser(DB_USER);
            cpds.setPassword(DB_PASSWORD);
            dataSource = cpds;
        } catch (PropertyVetoException e) {
            throw new RuntimeException("The provided driver name is not correct!");
        }
    }

    private ConnectionManagerImpl() {
    }

    public static IConnectionManager getInstance() {
        return INSTANCE;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
