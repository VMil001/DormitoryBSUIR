package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DormitoryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dormitoryImageId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Dormitory dormitory;

    @Lob
    @Column( length = 100000 )
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DormitoryImage that = (DormitoryImage) o;
        return dormitoryImageId != null && Objects.equals(dormitoryImageId, that.dormitoryImageId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
