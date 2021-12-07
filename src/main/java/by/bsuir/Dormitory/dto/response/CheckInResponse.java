package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckInResponse {
    private String credentials;
    private String dormitory;
    private String address;
    private String room;
    private String dateIn;
    private String dateOut;
    private Boolean active;
    private Long checkInId;
    private String email;
    private String gender;
}
