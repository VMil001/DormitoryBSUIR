package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @ManyToOne
    private User user;

    private Long number;
    private Date date;
    private String wishes;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Status {
        WAITING("В ожидании"),
        WAITING_CANCELED("В ожидании отмены"),
        CANCELED("Отменено"),
        REJECTED("Отказано"),
        CONFIRMED("Подтверждено");

        private final String type;

        Status(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        private static final Map<String, Status> LOOKUP_MAP = new HashMap<>();

        static {
            for (Status status : values()) {
                LOOKUP_MAP.put(status.getType(), status);
            }
        }

        public static Status getStatusByType(String type) {
            return LOOKUP_MAP.get(type);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Application that = (Application) o;
        return applicationId != null && Objects.equals(applicationId, that.applicationId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
