package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    private String number;
    private Long places;

    @ManyToOne
    private Dormitory dormitory;

    @Enumerated(EnumType.STRING)
    private User.Gender gender;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room that = (Room) o;
        return roomId != null && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
