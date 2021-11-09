package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.SignupRequest;
import by.bsuir.Dormitory.dto.response.UserResponse;
import by.bsuir.Dormitory.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    @Mapping(target = "registerDate", expression = "java(getCurrentDate())")
    @Mapping(target = "active", expression = "java(true)")
    @Mapping(target = "verified", expression = "java(false)")
    @Mapping(target = "role", expression = "java(User.Role.getRoleByType(signupRequest.getRole()))")
    @Mapping(target = "gender", expression = "java(User.Gender.getGenderByType(signupRequest.getGender()))")
    public abstract User map(SignupRequest signupRequest);

    @Mapping(target = "roleName", expression = "java(getRoleName(user))")
    @Mapping(target = "registerDate", expression = "java(getDate(user.getRegisterDate()))")
    public abstract UserResponse mapToDto(User user);

    String getRoleName(User user) {
        return user.getRole().getType();
    }

    String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(date.getTime());
    }

    Date getCurrentDate() {
        return new Date();
    }
}
