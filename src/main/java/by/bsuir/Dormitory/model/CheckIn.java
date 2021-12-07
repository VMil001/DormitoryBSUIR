package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checkInId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dormitory dormitory;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Room room;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private Date dateIn;
    private Date dateOut;
    private Boolean active;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Application application;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckIn that = (CheckIn) o;
        return checkInId != null && Objects.equals(checkInId, that.checkInId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
