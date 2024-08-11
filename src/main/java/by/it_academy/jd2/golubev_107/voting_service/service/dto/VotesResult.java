package by.it_academy.jd2.golubev_107.voting_service.service.dto;

import by.it_academy.jd2.golubev_107.voting_service.repository.entity.Comment;
import by.it_academy.jd2.golubev_107.voting_service.repository.entity.EArtist;
import by.it_academy.jd2.golubev_107.voting_service.repository.entity.EGenre;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class VotesResult {

    private final Map<EArtist, Integer> artistVotes;
    private final Map<EGenre, Integer> genreVotes;
    private final List<Comment> comments;

    public VotesResult(Map<EArtist, Integer> artistVotes, Map<EGenre, Integer> genreVotes, List<Comment> comments) {
        this.artistVotes = artistVotes;
        this.genreVotes = genreVotes;
        this.comments = comments;
    }

    public Map<EArtist, Integer> getArtistVotes() {
        return artistVotes;
    }

    public Map<EGenre, Integer> getGenreVotes() {
        return genreVotes;
    }

    public List<Comment> getAllComments() {
        return comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VotesResult result = (VotesResult) o;
        return Objects.equals(artistVotes, result.artistVotes) && Objects.equals(genreVotes, result.genreVotes) && Objects.equals(comments, result.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistVotes, genreVotes, comments);
    }

    @Override
    public String toString() {
        return "VotesResult{" +
                "artistVotes=" + artistVotes +
                ", genreVotes=" + genreVotes +
                ", comments=" + comments +
                '}';
    }
}
