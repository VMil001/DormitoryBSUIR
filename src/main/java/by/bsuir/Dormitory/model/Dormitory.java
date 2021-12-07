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
public class Dormitory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dormitoryId;

    private String name;
    private String address;

    @Lob
    @Column( length = 100000 )
    private String info;

    @Lob
    @Column( length = 100000 )
    private String imageURL;

    @ManyToOne
    private User manager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Dormitory that = (Dormitory) o;
        return dormitoryId != null && Objects.equals(dormitoryId, that.dormitoryId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
