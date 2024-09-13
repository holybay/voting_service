package by.it_academy.jd2.golubev_107.voting_service.storage.impl;

import by.it_academy.jd2.golubev_107.voting_service.storage.IVoteStorage;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.IConnectionManager;
import by.it_academy.jd2.golubev_107.voting_service.storage.connection.impl.ConnectionManagerImpl;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Genre;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Vote;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VoteStorageImpl implements IVoteStorage {

    private static final IVoteStorage instance = new VoteStorageImpl();
    private static final String INSERT_VOTE_QUERY = "INSERT INTO app.vote (artist_id, created_at) VALUES(?, ?) RETURNING id;";
    private static final String INSERT_CROSS_VOTE_GENRE_QUERY = "INSERT INTO app.cross_vote_genre (vote_id, genre_id) VALUES(?, ?);";
    private static final String SELECT_COUNT_VOTES_BY_ARTIST = """
            SELECT a.id AS artistId, count(v.artist_id) AS totalVotes\s
            FROM app.artist a\s
            LEFT JOIN app.vote v ON a.id = v.artist_id\s
            GROUP BY a.id;""";
    private static final String SELECT_COUNT_VOTES_BY_GENRE = """
            SELECT g.id AS genreId, count(cvg.vote_id) AS totalVotes\s
            FROM app.genre g\s
            LEFT JOIN app.cross_vote_genre cvg ON g.id = cvg.genre_id\s
            GROUP BY g.id;""";
    private final IConnectionManager connectionManager = ConnectionManagerImpl.getInstance();
    private final List<Comment> comments = new ArrayList<>();

    private VoteStorageImpl() {
    }

    public static IVoteStorage getInstance() {
        return instance;
    }

    @Override
    public void save(Vote vote) {
        try (Connection connection = connectionManager.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VOTE_QUERY);
            preparedStatement.setLong(1, vote.getArtist().getId());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            ResultSet resultSet = preparedStatement.executeQuery();
            Long id = null;
            if (resultSet.next()) {
                id = resultSet.getLong("id");
            }

            if (id != null) {
                for (Genre genre : vote.getGenres()) {
                    PreparedStatement crossVoteGenreInsertStmt = connection.prepareStatement(INSERT_CROSS_VOTE_GENRE_QUERY);
                    crossVoteGenreInsertStmt.setLong(1, id);
                    crossVoteGenreInsertStmt.setLong(2, genre.getId());
                    crossVoteGenreInsertStmt.executeUpdate();
                }
            }
            saveComment(vote);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }

    private void saveComment(Vote vote) {
        comments.add(vote.getComment());
    }

    @Override
    public Map<Long, Integer> getArtistResults() {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_VOTES_BY_ARTIST);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Integer> rawArtistVotes = new HashMap<>();
            while (resultSet.next()) {
                rawArtistVotes.put(resultSet.getLong("artistId"), resultSet.getInt("totalVotes"));
            }
            return rawArtistVotes;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }

    @Override
    public Map<Long, Integer> getGenreResults() {
        try (Connection connection = connectionManager.getConnection();) {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COUNT_VOTES_BY_GENRE);
            ResultSet resultSet = preparedStatement.executeQuery();
            Map<Long, Integer> rawGenreVotes = new HashMap<>();
            while (resultSet.next()) {
                rawGenreVotes.put(resultSet.getLong("genreId"), resultSet.getInt("totalVotes"));
            }
            return rawGenreVotes;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to execute a query!", e);
        }
    }

    @Override
    public List<Comment> getComments() {
        return List.copyOf(comments);
    }
}
