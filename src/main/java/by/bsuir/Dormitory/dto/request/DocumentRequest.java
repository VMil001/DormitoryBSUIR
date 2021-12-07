package by.bsuir.Dormitory.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequest {
    private Long rightId;
    private Long privilegeId;
    private Long applicationId;
    private String note;
}
