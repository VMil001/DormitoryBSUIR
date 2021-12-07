package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.exception.ApplicationNotFoundException;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.CheckIn;
import by.bsuir.Dormitory.dto.request.CheckInRequest;
import by.bsuir.Dormitory.dto.response.CheckInResponse;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.ApplicationRepository;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class CheckInMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Mapping(target = "user", expression = "java(getUser(checkInRequest.getUserId()))")
    @Mapping(target = "application", expression = "java(getApplication(checkInRequest.getApplicationId()))")
    @Mapping(target = "dormitory", expression = "java(getDormitory(checkInRequest.getDormitoryName()))")
    @Mapping(target = "dateIn", expression = "java(getCurrentDate())")
    @Mapping(target = "active", expression = "java(true)")
    public abstract CheckIn map(CheckInRequest checkInRequest);

    @Mapping(target = "credentials", expression = "java(checkIn.getUser().getCredentials())")
    @Mapping(target = "dormitory", source = "checkIn.dormitory.name")
    @Mapping(target = "address", source = "checkIn.dormitory.address")
    @Mapping(target = "room", source = "checkIn.room.number")
    @Mapping(target = "email", source = "checkIn.user.email")
    @Mapping(target = "gender", expression = "java(checkIn.getUser().getGender().getType())")
    public abstract CheckInResponse mapToDto(CheckIn checkIn);

    User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    Application getApplication(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    Dormitory getDormitory(String name) {
        return dormitoryRepository.findByName(name)
                .orElseThrow(() -> new DormitoryNotFoundException(name));
    }

    Date getCurrentDate() {
        return new Date();
    }
}
