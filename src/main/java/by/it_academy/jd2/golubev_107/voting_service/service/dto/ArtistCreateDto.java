package by.it_academy.jd2.golubev_107.voting_service.service.dto;

import java.util.Objects;

public class ArtistCreateDto {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistCreateDto that = (ArtistCreateDto) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "ArtistInDto{" +
                "artistName='" + name + '\'' +
                '}';
    }
}
