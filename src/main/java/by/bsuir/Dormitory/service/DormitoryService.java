package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.DormitoryInfoRequest;
import by.bsuir.Dormitory.dto.request.DormitoryRequest;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.mapper.DormitoryMapper;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class DormitoryService {

    private final DormitoryRepository dormitoryRepository;

    private final DormitoryMapper dormitoryMapper;

    private final RoomService roomService;
    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<DormitoryResponse> getAll() {
        return dormitoryRepository.findAll()
                .stream()
                .map(dormitoryMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public DormitoryResponse getById(Long id) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
        return dormitoryMapper.mapToDto(dormitory);
    }
    public void deleteById(Long id) {
        dormitoryRepository.deleteById(id);
    }

    public void save(DormitoryRequest dormitoryRequest) {
        Dormitory dormitory = dormitoryMapper.map(dormitoryRequest);
        dormitoryRepository.save(dormitory);
    }

    @Transactional(readOnly = true)
    public List<DormitoryResponse> getAllFree() {
        return dormitoryRepository.findAll()
                .stream()
                .filter((dormitory -> roomService.countFreePlacesByDormitory(dormitory) > 0))
                .map(dormitoryMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public DormitoryResponse getCurrent() {
        User user = authService.getCurrentUser();
        Dormitory dormitory = dormitoryRepository.findByManager(user)
                .orElseThrow(() -> new DormitoryNotFoundException(user));
        return dormitoryMapper.mapToDto(dormitory);
    }

    public DormitoryResponse updateInfo(Long id, DormitoryInfoRequest request) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
        dormitory.setInfo(request.getInfo());
        return dormitoryMapper.mapToDto(dormitoryRepository.save(dormitory));
    }

    public DormitoryResponse updateName(Long id, DormitoryInfoRequest request) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
        dormitory.setName(request.getInfo());
        return dormitoryMapper.mapToDto(dormitoryRepository.save(dormitory));
    }

    public DormitoryResponse updateAddress(Long id, DormitoryInfoRequest request) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
        dormitory.setAddress(request.getInfo());
        return dormitoryMapper.mapToDto(dormitoryRepository.save(dormitory));
    }

    public DormitoryResponse updateMainImage(Long id, DormitoryInfoRequest request) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));
        dormitory.setImageURL(request.getInfo());
        return dormitoryMapper.mapToDto(dormitoryRepository.save(dormitory));
    }
}
