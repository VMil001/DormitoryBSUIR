package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.DormitoryRequest;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.model.Dormitory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DormitoryMapper {
    Dormitory map(DormitoryRequest dormitoryRequest);

    DormitoryResponse mapToDto(Dormitory dormitory);
}
