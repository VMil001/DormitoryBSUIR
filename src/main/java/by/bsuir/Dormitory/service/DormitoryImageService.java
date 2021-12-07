package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.DormitoryImageRequest;
import by.bsuir.Dormitory.dto.response.DormitoryImageResponse;
import by.bsuir.Dormitory.exception.DormitoryImageNotFoundException;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.mapper.DormitoryImageMapper;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.DormitoryImage;
import by.bsuir.Dormitory.repository.DormitoryImageRepository;


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
public class DormitoryImageService {

    private final DormitoryImageRepository dormitoryImageRepository;
    private final DormitoryRepository dormitoryRepository;

    private final DormitoryImageMapper dormitoryImageMapper;

    @Transactional(readOnly = true)
    public List<DormitoryImageResponse> getAll() {
        return dormitoryImageRepository.findAll()
                .stream()
                .map(dormitoryImageMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public DormitoryImageResponse getById(Long id) {
        DormitoryImage dormitoryImage = dormitoryImageRepository.findById(id)
                .orElseThrow(() -> new DormitoryImageNotFoundException(id));
        return dormitoryImageMapper.mapToDto(dormitoryImage);
    }

    public void deleteById(Long id) {
        dormitoryImageRepository.deleteById(id);
    }

    public void save(DormitoryImageRequest dormitoryImageRequest) {
        DormitoryImage dormitoryImage = dormitoryImageMapper.map(dormitoryImageRequest);
        dormitoryImageRepository.save(dormitoryImage);
    }

    @Transactional(readOnly = true)
    public List<DormitoryImageResponse> getAllByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return dormitoryImageRepository.findAllByDormitory(dormitory)
                .stream()
                .map(dormitoryImageMapper::mapToDto)
                .collect(toList());
    }
}
