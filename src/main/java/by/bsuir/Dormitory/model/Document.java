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
public class Document implements Comparable<Document>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    @ManyToOne
    private Right right;

    @ManyToOne
    private Privilege privilege;

    private String note;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Application application;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Document that = (Document) o;
        return documentId != null && Objects.equals(documentId, that.documentId);
    }

    @Override
    public int hashCode() {
        return 0;
    }


    @Override
    public int compareTo(Document o) {
        return right.compareTo(o.right);
    }
}
