package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.RoomRequest;
import by.bsuir.Dormitory.dto.response.RoomResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class RoomMapper {
    @Autowired
    private DormitoryRepository dormitoryRepository;
    @Autowired
    private UserRepository userRepository;

    @Mapping(target = "dormitory", expression = "java(getDormitory(roomRequest.getDormitoryId()))")
    @Mapping(target = "gender", constant = "DEFAULT")
    public abstract Room map(RoomRequest roomRequest);

    @Mapping(target = "freePlaces", expression = "java(getFreePlaces(room))")
    @Mapping(target = "gender", source = "room.gender.type")
    @Mapping(target = "dormitory", source = "room.dormitory.name")
    public abstract RoomResponse mapToDto(Room room);

    Dormitory getDormitory(long id) {
        return dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
    }

    Long getFreePlaces(Room room) {
        return room.getPlaces() - userRepository.countByRoom(room);
    }
}
