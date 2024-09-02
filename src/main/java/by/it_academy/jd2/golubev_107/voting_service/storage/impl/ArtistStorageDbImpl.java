package by.it_academy.jd2.golubev_107.voting_service.storage.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.IArtistStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl.ConnectionManagerImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistStorageDbImpl implements IArtistStorage {

    private static final IArtistStorage INSTANCE = new ArtistStorageDbImpl();
    public static final String CREATE_ARTIST_QUERY = "INSERT INTO app.artist (\"name\") VALUES(?) RETURNING id;";
    public static final String SELECT_BY_ID_QUERY = "SELECT id, \"name\" FROM app.artist WHERE id = ?;";
    public static final String SELECT_ALL_ARTISTS_BY_NAME_QUERY = "SELECT id, \"name\" FROM app.artist WHERE \"name\" = ?;";
    public static final String SELECT_ALL_QUERY = "SELECT id, \"name\" FROM app.artist;";
    private final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();

    public static IArtistStorage getInstance() {
        return INSTANCE;
    }

    private ArtistStorageDbImpl() {
    }

    @Override
    public Long create(Artist artist) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ARTIST_QUERY);
            preparedStatement.setString(1, artist.getName());
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
    public Artist readById(Long id) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID_QUERY);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getLong("id"));
                artist.setName(resultSet.getString("name"));
                return artist;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
        return null;
    }

    @Override
    public List<Artist> readByName(String name) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ARTISTS_BY_NAME_QUERY);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getLong("id"));
                artist.setName(resultSet.getString("name"));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }

    @Override
    public List<Artist> readAll() {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Artist> artists = new ArrayList<>();
            while (resultSet.next()) {
                Artist artist = new Artist();
                artist.setId(resultSet.getLong("id"));
                artist.setName(resultSet.getString("name"));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }
}
