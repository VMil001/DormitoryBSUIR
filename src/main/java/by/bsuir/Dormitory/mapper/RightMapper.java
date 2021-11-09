package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.RightRequest;
import by.bsuir.Dormitory.dto.response.RightResponse;
import by.bsuir.Dormitory.model.Right;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RightMapper {
    Right map(RightRequest rightRequest);

    RightResponse mapToDto(Right right);
}
