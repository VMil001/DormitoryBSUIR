package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private String email;
    private String role;
    private String imgURL;

    private String registerDate;
    private Boolean active;
    private Boolean verified;

    private String credentials;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String address;
    private String phone;

    private String groupNumber;
    private String faculty;
    private Integer course;
    private String gender;

    private String dormitory;
    private String room;
    private Long queueNumber;
}
