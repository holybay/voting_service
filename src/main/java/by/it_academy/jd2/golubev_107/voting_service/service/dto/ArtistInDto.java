package by.it_academy.jd2.golubev_107.voting_service.service.dto;

import java.util.Objects;

public class ArtistInDto {

    private String artistName;

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
        ArtistInDto that = (ArtistInDto) o;
        return Objects.equals(artistName, that.artistName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(artistName);
    }

    @Override
    public String toString() {
        return "ArtistInDto{" +
                "artistName='" + artistName + '\'' +
                '}';
    }
}
