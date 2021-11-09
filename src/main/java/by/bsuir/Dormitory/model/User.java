package by.bsuir.Dormitory.model;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotEmpty
    @Column(unique = true)
    private String username;

    @NotEmpty
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private Date registerDate;
    private Boolean active;
    private Boolean verified;

    private String firstName;
    private String lastName;
    private String patronymic;
    private String address;
    private String phone;

    private String groupNumber;
    private String faculty;
    private Integer course;

    @ManyToOne
    private Dormitory dormitory;

    @ManyToOne
    private Room room;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Role implements GrantedAuthority {
        ADMIN("Администратор"),
        MANAGER("Заведующий общежитием"),
        STUDENT("Студент");

        private final String type;

        Role(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        private static final Map<String, Role> LOOKUP_MAP = new HashMap<>();

        static {
            for (Role role : values()) {
                LOOKUP_MAP.put(role.getType(), role);
            }
        }

        public static Role getRoleByType(String type) {
            return LOOKUP_MAP.get(type);
        }

        @Override
        public String getAuthority() {
            return toString();
        }
    }

    public enum Gender {
        DEFAULT("Не указано"),
        MEN("Мужской"),
        WOMEN("Женский");

        private final String type;

        Gender(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        private static final Map<String, Gender> LOOKUP_MAP = new HashMap<>();

        static {
            for (Gender gender : values()) {
                LOOKUP_MAP.put(gender.getType(), gender);
            }
        }

        public static Gender getGenderByType(String type) {
            return LOOKUP_MAP.get(type);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
