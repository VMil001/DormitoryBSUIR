package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String text;
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Dormitory dormitory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment that = (Comment) o;
        return commentId != null && Objects.equals(commentId, that.commentId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
