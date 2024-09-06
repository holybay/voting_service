package by.it_academy.jd2.golubev_107.voting_service.service.dto.vote;

import by.it_academy.jd2.golubev_107.voting_service.service.dto.artist.ArtistVotingDtoSimple;
import by.it_academy.jd2.golubev_107.voting_service.storage.entity.Comment;

import java.util.Arrays;
import java.util.Objects;

public class VoteInptDto {

    private ArtistVotingDtoSimple artist;
    private String[] genres;
    private Comment comment;

    public ArtistVotingDtoSimple getArtist() {
        return artist;
    }

    public void setArtist(ArtistVotingDtoSimple artist) {
        this.artist = artist;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
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
        return Objects.equals(artist, that.artist) && Objects.deepEquals(genres, that.genres) && Objects.equals(comment, that.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artist, Arrays.hashCode(genres), comment);
    }

    @Override
    public String toString() {
        return "VoteInptDto{" + "artist=" + artist + ", genres=" + Arrays.toString(genres) + ", comment=" + comment + '}';
    }
}
