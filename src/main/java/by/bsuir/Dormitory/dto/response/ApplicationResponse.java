package by.bsuir.Dormitory.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {
    private Long applicationId;

    private String dormitoryWish;
    private String roomWish;
    private String studentWish;
    private String wishes;

    private Long number;
    private String status;
    private String date;

    private String message;
    private String finishDate;
    private String dormitory;

    private Boolean evicted;

    private Long userId;
    private String course;
    private String faculty;
    private String gender;
    private String credentials;

    private List<DocumentResponse> documents;
}
