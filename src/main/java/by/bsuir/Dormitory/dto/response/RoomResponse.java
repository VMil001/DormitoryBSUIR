package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponse {
    private Long roomId;
    private String number;
    private Long places;
    private Long freePlaces;
    private String dormitory;
    private String gender;
    private List<String> students;
}
