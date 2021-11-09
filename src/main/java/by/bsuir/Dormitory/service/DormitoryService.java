package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.DormitoryRequest;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.mapper.DormitoryMapper;
import by.bsuir.Dormitory.model.Dormitory;
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
}
