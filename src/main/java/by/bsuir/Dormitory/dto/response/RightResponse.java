package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RightResponse {
    private Long rightId;
    private String name;
    private Double factor;
}
