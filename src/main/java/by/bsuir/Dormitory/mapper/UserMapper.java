package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.SignupRequest;
import by.bsuir.Dormitory.dto.response.UserResponse;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.CheckIn;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.ApplicationRepository;
import by.bsuir.Dormitory.repository.CheckInRepository;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Mapping(target = "registerDate", expression = "java(getCurrentDate())")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "verified", expression = "java(false)")
    @Mapping(target = "role", expression = "java(User.Role.getRoleByType(signupRequest.getRole()))")
    @Mapping(target = "gender", expression = "java(User.Gender.getGenderByType(signupRequest.getGender()))")
    public abstract User map(SignupRequest signupRequest);

    @Mapping(target = "role", expression = "java(user.getRole().getType())")
    @Mapping(target = "registerDate", expression = "java(getDate(user.getRegisterDate()))")
    @Mapping(target = "dormitory", expression = "java(getDormitory(user))")
    @Mapping(target = "room", expression = "java(getRoom(user))")
    @Mapping(target = "gender", expression = "java(getGender(user))")
    @Mapping(target = "queueNumber", expression = "java(getQueueNumber(user))")
    @Mapping(target = "credentials", expression = "java(user.getCredentials())")
    public abstract UserResponse mapToDto(User user);

    String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date.getTime());
    }

    Date getCurrentDate() {
        return new Date();
    }

    String getDormitory(User user) {
        if (user.getRole() == User.Role.STUDENT) {
            Optional<CheckIn> checkIn = checkInRepository.findByUserAndActiveIsTrue(user);
            return checkIn.isPresent() ? checkIn.get().getDormitory().getName() : "";
        } else if (user.getRole() == User.Role.MANAGER) {
            Optional<Dormitory> dormitory = dormitoryRepository.findByManager(user);
            return dormitory.isPresent() ? dormitory.get().getName() : "";
        }
        return "";
    }

    String getRoom(User user) {
        Optional<CheckIn> checkIn = checkInRepository.findByUserAndActiveIsTrue(user);
        return checkIn.isPresent() && checkIn.get().getRoom() != null ? checkIn.get().getRoom().getNumber() : "";
    }

    Long getQueueNumber(User user) {
        Optional<Application> application = applicationRepository.findFirstByUserOrderByDateDesc(user);
        return application.isPresent() ? application.get().getNumber() : 0;
    }

    String getGender(User user) {
        return user.getGender() == null ? "" : user.getGender().getType();
    }
}
