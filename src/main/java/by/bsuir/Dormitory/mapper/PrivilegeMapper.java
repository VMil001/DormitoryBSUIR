package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.PrivilegeRequest;
import by.bsuir.Dormitory.dto.response.PrivilegeResponse;
import by.bsuir.Dormitory.exception.RightNotFoundException;
import by.bsuir.Dormitory.model.Privilege;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.repository.RightRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PrivilegeMapper {
    @Autowired
    private RightRepository rightRepository;

    @Mapping(target = "right", expression = "java(getRight(privilegeRequest.getRightId()))")
    public abstract Privilege map(PrivilegeRequest privilegeRequest);

    @Mapping(target = "right", source = "privilege.right.name")
    public abstract PrivilegeResponse mapToDto(Privilege privilege);

    Right getRight(Long id) {
        return rightRepository.findById(id)
                .orElseThrow(() -> new RightNotFoundException(id));
    }
}
