package by.it_academy.jd2.golubev_107.voting_service.service.dto;

import java.util.Objects;

public class ArtistVotingDtoSimple {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtistVotingDtoSimple that = (ArtistVotingDtoSimple) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ArtistVotingDtoSimple{" +
                "id=" + id +
                '}';
    }
}

