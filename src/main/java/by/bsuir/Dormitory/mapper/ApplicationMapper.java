package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.ApplicationRequest;
import by.bsuir.Dormitory.dto.response.ApplicationResponse;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {
    @Autowired
    private AuthService authService;

    @Mapping(target = "date", expression = "java(getCurrentDate())")
    @Mapping(target = "user", expression = "java(getCurrentUser())")
    public abstract Application map(ApplicationRequest applicationRequest);

    public abstract ApplicationResponse mapToDto(Application application);

    Date getCurrentDate() {
        return new Date();
    }

    User getCurrentUser() {
        return authService.getCurrentUser();
    }
}
