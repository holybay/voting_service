package by.it_academy.jd2.golubev_107.voting_service.repository.entity;

import java.util.List;
import java.util.Objects;

public class Vote {

    private EArtist artistName;
    private List<EGenre> genres;
    private Comment comment;

    public EArtist getArtistName() {
        return artistName;
    }

    public void setArtistName(EArtist artistName) {
        this.artistName = artistName;
    }

    public List<EGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<EGenre> genres) {
        this.genres = genres;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return artistName == vote.artistName && Objects.equals(genres, vote.genres) && Objects.equals(comment, vote.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, genres, comment);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "artistName=" + artistName +
                ", genres=" + genres +
                ", comment=" + comment +
                '}';
    }
}


