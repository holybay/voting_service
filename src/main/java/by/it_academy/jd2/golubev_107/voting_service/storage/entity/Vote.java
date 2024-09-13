package by.it_academy.jd2.golubev_107.voting_service.storage.entity;

import java.util.List;
import java.util.Objects;

public class Vote {

    private Artist artist;
    private List<Genre> genres;
    private Comment comment;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
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
        return Objects.equals(artist, vote.artist) && Objects.equals(genres, vote.genres) && Objects.equals(comment, vote.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, genres, comment);
    }

    @Override
    public String toString() {
        return "Vote{" +
                "artist=" + artist +
                ", genres=" + genres +
                ", comment=" + comment +
                '}';
    }
}


