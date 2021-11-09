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
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long privilegeId;

    private String name;

    @ManyToOne
    private Right right;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Privilege that = (Privilege) o;
        return privilegeId != null && Objects.equals(privilegeId, that.privilegeId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
