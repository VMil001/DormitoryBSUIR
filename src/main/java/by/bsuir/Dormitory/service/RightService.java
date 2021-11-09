package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.RightRequest;
import by.bsuir.Dormitory.dto.response.RightResponse;
import by.bsuir.Dormitory.exception.RightNotFoundException;
import by.bsuir.Dormitory.mapper.RightMapper;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.repository.RightRepository;
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
public class RightService {

    private final RightRepository rightRepository;

    private final RightMapper rightMapper;

    @Transactional(readOnly = true)
    public List<RightResponse> getAll() {
        return rightRepository.findAll()
                .stream()
                .map(rightMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public RightResponse getById(Long id) {
        Right right = rightRepository.findById(id)
                .orElseThrow(() -> new RightNotFoundException(id));
        return rightMapper.mapToDto(right);
    }

    public void deleteById(Long id) {
        rightRepository.deleteById(id);
    }

    public void save(RightRequest rightRequest) {
        Right right = rightMapper.map(rightRequest);
        rightRepository.save(right);
    }
}
