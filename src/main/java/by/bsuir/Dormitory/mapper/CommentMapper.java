package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.CommentRequest;
import by.bsuir.Dormitory.dto.response.CommentResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.model.Comment;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    private AuthService authService;
    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Mapping(target = "user", expression = "java(getCurrentUser())")
    @Mapping(target = "date", expression = "java(getCurrentDate())")
    @Mapping(target = "dormitory", expression = "java(getDormitory(commentRequest.getDormitoryId()))")
    public abstract Comment map(CommentRequest commentRequest);

    @Mapping(target = "user", source = "comment.user.username")
    @Mapping(target = "dormitory", source = "comment.dormitory.name")
    @Mapping(target = "date", expression = "java(getDate(comment.getDate()))")
    public abstract CommentResponse mapToDto(Comment comment);

    Date getCurrentDate() {
        return new Date();
    }

    String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
        return formatter.format(date.getTime());
    }

    User getCurrentUser() {
        return authService.getCurrentUser();
    }

    Dormitory getDormitory(Long dormitoryId) {
        return dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
    }
}
