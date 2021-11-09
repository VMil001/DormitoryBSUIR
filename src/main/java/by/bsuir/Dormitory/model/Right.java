package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "settlement_right")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Right implements Comparable<Right> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rightId;

    private String name;
    private Double factor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Right that = (Right) o;
        return rightId != null && Objects.equals(rightId, that.rightId);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public int compareTo(Right o) {
        return factor.compareTo(o.factor);
    }
}
