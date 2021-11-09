package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.DocumentRequest;
import by.bsuir.Dormitory.dto.response.DocumentResponse;
import by.bsuir.Dormitory.exception.PrivilegeNotFoundException;
import by.bsuir.Dormitory.exception.RightNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.model.Document;
import by.bsuir.Dormitory.model.Privilege;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.PrivilegeRepository;
import by.bsuir.Dormitory.repository.RightRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RightRepository rightRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Mapping(target = "user", expression = "java(getUser(documentRequest.getUserId()))")
    @Mapping(target = "right", expression = "java(getRight(documentRequest.getRightId()))")
    @Mapping(target = "privilege", expression = "java(getPrivilege(documentRequest.getPrivilegeId()))")
    public abstract Document map(DocumentRequest documentRequest);

    @Mapping(target = "username", source = "document.user.username")
    @Mapping(target = "right", source = "document.right.name")
    @Mapping(target = "privilege", expression = "java(getPrivilegeName(document.getPrivilege()))")
    public abstract DocumentResponse mapToDto(Document document);

    User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    Right getRight(Long id) {
        return rightRepository.findById(id)
                .orElseThrow(() -> new RightNotFoundException(id));
    }

    Privilege getPrivilege(Long id) {
        if (id == 0) return null;
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new PrivilegeNotFoundException(id));
    }

    String getPrivilegeName(Privilege privilege) {
        return privilege != null ? privilege.getName() : "";
    }
}
