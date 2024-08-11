package by.it_academy.jd2.golubev_107.voting_service.storage.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment {

    private final String comment;
    private final LocalDateTime dateVoted;

    public Comment(String comment, LocalDateTime dateVoted) {
        this.comment = comment;
        this.dateVoted = dateVoted;
    }

    public String getTextComment() {
        return comment;
    }

    public LocalDateTime getDateVoted() {
        return dateVoted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return Objects.equals(comment, comment1.comment) && Objects.equals(dateVoted, comment1.dateVoted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(comment, dateVoted);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", dateVoted=" + dateVoted +
                '}';
    }
}
