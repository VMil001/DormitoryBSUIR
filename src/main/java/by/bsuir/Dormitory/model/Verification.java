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
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long verificationId;
    private String token;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    private Date expiryDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Verification that = (Verification) o;
        return verificationId != null && Objects.equals(verificationId, that.verificationId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
