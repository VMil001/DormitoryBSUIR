package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DormitoryResponse {
    private Long dormitoryId;
    private String name;
    private String address;
    private Long freePlaces;
    private String info;
    private String imageURL;
}
