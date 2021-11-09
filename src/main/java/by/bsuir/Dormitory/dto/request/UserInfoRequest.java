package by.bsuir.Dormitory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoRequest {
    private String lastName;
    private String firstName;
    private String patronymic;
    private String address;
    private String phone;
    private String gender;
}
