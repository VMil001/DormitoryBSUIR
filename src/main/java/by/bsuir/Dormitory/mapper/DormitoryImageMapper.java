package by.bsuir.Dormitory.mapper;

import by.bsuir.Dormitory.dto.request.DormitoryImageRequest;
import by.bsuir.Dormitory.dto.response.DormitoryImageResponse;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.DormitoryImage;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DormitoryImageMapper {
    @Autowired
    private DormitoryRepository dormitoryRepository;

    @Mapping(target = "dormitory", expression = "java(getDormitory(request.getDormitoryId()))")
    public abstract DormitoryImage map(DormitoryImageRequest request);

    public abstract DormitoryImageResponse mapToDto(DormitoryImage dormitoryImage);

    Dormitory getDormitory(Long id) {
        return dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
    }
}
