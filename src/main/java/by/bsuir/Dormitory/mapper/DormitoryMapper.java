package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.DormitoryRequest;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.repository.CheckInRepository;
import by.bsuir.Dormitory.repository.RoomRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DormitoryMapper {
    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private RoomRepository roomRepository;

    public abstract Dormitory map(DormitoryRequest dormitoryRequest);

    @Mapping(target = "freePlaces", expression = "java(getFreePlaces(dormitory))")
    public abstract DormitoryResponse mapToDto(Dormitory dormitory);

    Long getFreePlaces(Dormitory dormitory) {
        Long places = roomRepository.countPlacesByDormitory(dormitory);
        if (places == null)
            return 0L;
        return places - checkInRepository.countByDormitoryAndActiveIsTrue(dormitory);
    }
}
