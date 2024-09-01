package by.it_academy.jd2.golubev_107.voting_service.service.dto;

import java.util.Objects;

public class ArtistOutDto {

    private Long id;
    private String artistName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistOutDto that = (ArtistOutDto) o;
        return Objects.equals(id, that.id) && Objects.equals(artistName, that.artistName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, artistName);
    }

    @Override
    public String toString() {
        return "ArtistOutDto{" +
                "id=" + id +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}
