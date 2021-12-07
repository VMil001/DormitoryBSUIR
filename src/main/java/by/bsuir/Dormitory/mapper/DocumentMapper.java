package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.DocumentRequest;
import by.bsuir.Dormitory.dto.response.DocumentResponse;
import by.bsuir.Dormitory.exception.ApplicationNotFoundException;
import by.bsuir.Dormitory.exception.PrivilegeNotFoundException;
import by.bsuir.Dormitory.exception.RightNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.model.*;
import by.bsuir.Dormitory.repository.ApplicationRepository;
import by.bsuir.Dormitory.repository.PrivilegeRepository;
import by.bsuir.Dormitory.repository.RightRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DocumentMapper {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private RightRepository rightRepository;
    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Mapping(target = "application", expression = "java(getApplication(documentRequest.getApplicationId()))")
    @Mapping(target = "right", expression = "java(getRight(documentRequest.getRightId()))")
    @Mapping(target = "privilege", expression = "java(getPrivilege(documentRequest.getPrivilegeId()))")
    public abstract Document map(DocumentRequest documentRequest);

    @Mapping(target = "username", source = "document.application.user.username")
    @Mapping(target = "right", source = "document.right.name")
    @Mapping(target = "privilege", expression = "java(getPrivilegeName(document.getPrivilege()))")
    public abstract DocumentResponse mapToDto(Document document);

    Application getApplication(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
    }

    Right getRight(Long id) {
        return rightRepository.findById(id)
                .orElseThrow(() -> new RightNotFoundException(id));
    }

    Privilege getPrivilege(Long id) {
        if (id == null || id == 0) return null;
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new PrivilegeNotFoundException(id));
    }

    String getPrivilegeName(Privilege privilege) {
        return privilege != null ? privilege.getName() : "";
    }
}
