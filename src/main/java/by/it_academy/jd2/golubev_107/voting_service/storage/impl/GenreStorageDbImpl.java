package by.it_academy.jd2.golubev_107.voting_service.storage.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.IGenreStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl.ConnectionManagerImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreStorageDbImpl implements IGenreStorage {
    private static final IGenreStorage INSTANCE = new GenreStorageDbImpl();
    private final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    public static final String CREATE_GENRE_QUERY = "INSERT INTO app.genre (name) VALUES(?) RETURNING id;";
    public static final String SELECT_BY_ID_QUERY = "SELECT id, name FROM app.genre WHERE id = ?;";
    public static final String SELECT_BY_NAME_QUERY = "SELECT id, name FROM app.genre WHERE name = ?;";
    public static final String SELECT_ALL_QUERY = "SELECT id, name FROM app.genre;";

    private GenreStorageDbImpl() {
    }

    public static IGenreStorage getInstance() {
        return INSTANCE;
    }

    @Override
    public Long create(Genre genre) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GENRE_QUERY);
            preparedStatement.setString(1, genre.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
        return null;
    }

    @Override
    public Genre readById(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("id"));
                genre.setName(resultSet.getString("name"));
                return genre;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
        return null;
    }

    @Override
    public Genre readByName(String name) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_NAME_QUERY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("id"));
                genre.setName(resultSet.getString("name"));
                return genre;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
        return null;
    }

    @Override
    public List<Genre> readAll() {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Genre> genres = new ArrayList<>();
            while (resultSet.next()) {
                Genre genre = new Genre();
                genre.setId(resultSet.getLong("id"));
                genre.setName(resultSet.getString("name"));
                genres.add(genre);
            }
            return genres;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }
}
