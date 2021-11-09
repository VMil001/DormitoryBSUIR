package by.bsuir.Dormitory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private String number;
    private Long places;
    private Long dormitoryId;
}
