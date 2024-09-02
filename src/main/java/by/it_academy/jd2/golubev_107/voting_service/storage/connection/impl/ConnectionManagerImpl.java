package by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManagerImpl implements IConnectionManager {

    private static final IConnectionManager INSTANCE = new ConnectionManagerImpl();
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/votes";
    private final Properties props;

    {
        try {
            Class.forName("org.postgresql.Driver");
            props = new Properties();
            props.setProperty("user", "postgres");
            props.setProperty("password", "postgres");
            props.setProperty("ssl", "false");
        } catch (ClassNotFoundException e) {
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
        return DriverManager.getConnection(DB_URL, props);
    }
}
