package by.it_academy.jd2.golubev_107.voting_service.service.dto.vote;

import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.service.dto.genre.GenreVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;

import java.util.List;
import java.util.Objects;

public class VoteInptDto {

    private ArtistVotingDtoSimple artist;
    private List<GenreVotingDtoSimple> genres;
    private Comment comment;

    public ArtistVotingDtoSimple getArtist() {
        return artist;
    }

    public void setArtist(ArtistVotingDtoSimple artist) {
        this.artist = artist;
    }

    public List<GenreVotingDtoSimple> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreVotingDtoSimple> genres) {
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
        VoteInptDto that = (VoteInptDto) o;
        return Objects.equals(artist, that.artist) && Objects.equals(genres, that.genres) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, genres, comment);
    }

    @Override
    public String toString() {
        return "VoteInptDto{" +
                "artist=" + artist +
                ", genres=" + genres +
                ", comment=" + comment +
                '}';
    }
}
