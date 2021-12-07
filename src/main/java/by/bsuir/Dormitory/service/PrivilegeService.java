package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.PrivilegeRequest;
import by.bsuir.Dormitory.dto.response.PrivilegeResponse;
import by.bsuir.Dormitory.exception.PrivilegeNotFoundException;
import by.bsuir.Dormitory.exception.RightNotFoundException;
import by.bsuir.Dormitory.mapper.PrivilegeMapper;
import by.bsuir.Dormitory.model.Privilege;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.repository.PrivilegeRepository;
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
public class PrivilegeService {

    private final PrivilegeRepository privilegeRepository;
    private final RightRepository rightRepository;

    private final PrivilegeMapper privilegeMapper;

    @Transactional(readOnly = true)
    public List<PrivilegeResponse> getAll() {
        return privilegeRepository.findAll()
                .stream()
                .map(privilegeMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PrivilegeResponse getById(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new PrivilegeNotFoundException(id));
        return privilegeMapper.mapToDto(privilege);
    }

    public void deleteById(Long id) {
        privilegeRepository.deleteById(id);
    }

    public void save(PrivilegeRequest privilegeRequest) {
        Privilege privilege = privilegeMapper.map(privilegeRequest);
        privilegeRepository.save(privilege);
    }

    @Transactional(readOnly = true)
    public List<PrivilegeResponse> getAllByRight(Long rightId) {
        Right right = rightRepository.findById(rightId)
                .orElseThrow(() -> new RightNotFoundException(rightId));

        return privilegeRepository.findAllByRight(right)
                .stream()
                .map(privilegeMapper::mapToDto)
                .collect(toList());
    }
}
