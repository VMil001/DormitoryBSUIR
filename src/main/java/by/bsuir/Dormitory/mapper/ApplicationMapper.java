package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.ApplicationRequest;
import by.bsuir.Dormitory.dto.response.ApplicationResponse;
import by.bsuir.Dormitory.dto.response.DocumentResponse;
import by.bsuir.Dormitory.exception.CheckInNotFoundException;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.CheckInRepository;
import by.bsuir.Dormitory.repository.DocumentRepository;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.service.AuthService;
import by.bsuir.Dormitory.service.DocumentService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Mapper(componentModel = "spring")
public abstract class ApplicationMapper {
    @Autowired
    private AuthService authService;

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private DocumentMapper documentMapper;

    @Mapping(target = "date", expression = "java(getCurrentDate())")
    @Mapping(target = "user", expression = "java(getCurrentUser())")
    @Mapping(target = "status", expression = "java(Application.Status.WAITING)")
    public abstract Application map(ApplicationRequest applicationRequest);

    @Mapping(target = "status", expression = "java(application.getStatus().getType())")
    @Mapping(target = "date", expression = "java(getDate(application.getDate()))")
    @Mapping(target = "finishDate", expression = "java(getDate(application.getFinishDate()))")
    @Mapping(target = "course", source = "application.user.course")
    @Mapping(target = "faculty", source = "application.user.faculty")
    @Mapping(target = "gender", expression = "java(getGender(application.getUser()))")
    @Mapping(target = "credentials", expression = "java(getCredentials(application.getUser()))")
    @Mapping(target = "documents", expression = "java(getDocuments(application))")
    @Mapping(target = "userId", source = "application.user.userId")
    @Mapping(target = "dormitory", expression = "java(getDormitory(application))")
    @Mapping(target = "evicted", expression = "java(getEvicted(application))")
    public abstract ApplicationResponse mapToDto(Application application);

    Date getCurrentDate() {
        return new Date();
    }

    User getCurrentUser() {
        return authService.getCurrentUser();
    }

    String getDate(Date date) {
        if(date == null) return "";

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return formatter.format(date.getTime());
    }

    String getGender(User user) {
        return user.getGender() == null ? "" : user.getGender().getType();
    }

    String getCredentials(User user) {
        return user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic();
    }

    List<DocumentResponse> getDocuments(Application application) {
        return documentRepository.findAllByApplicationOrderByRight(application)
                .stream()
                .map(documentMapper::mapToDto)
                .collect(toList());
    }

    String getDormitory(Application application) {
        if(application.getStatus() != Application.Status.CONFIRMED) return "";

        return checkInRepository.findByApplication(application)
                .orElseThrow(() -> new CheckInNotFoundException(application))
                .getDormitory()
                .getName();
    }

    Boolean getEvicted(Application application) {
        if(application.getStatus() != Application.Status.CONFIRMED) return false;

        return !checkInRepository.findByApplication(application)
                .orElseThrow(() -> new CheckInNotFoundException(application))
                .getActive();
    }
}
