package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.model.PasswordRecovery;
import by.bsuir.Dormitory.dto.request.PasswordRecoveryRequest;
import by.bsuir.Dormitory.dto.response.PasswordRecoveryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PasswordRecoveryMapper {
    PasswordRecovery map(PasswordRecoveryRequest passwordRecoveryRequest);

    PasswordRecoveryResponse mapToDto(PasswordRecovery passwordRecovery);
}
