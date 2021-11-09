package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.PasswordRecoveryRequest;
import by.bsuir.Dormitory.dto.response.PasswordRecoveryResponse;
import by.bsuir.Dormitory.exception.PasswordRecoveryNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.PasswordRecoveryMapper;
import by.bsuir.Dormitory.model.PasswordRecovery;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.PasswordRecoveryRepository;


import by.bsuir.Dormitory.repository.UserRepository;
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
public class PasswordRecoveryService {

    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final UserRepository userRepository;

    private final PasswordRecoveryMapper passwordRecoveryMapper;

    @Transactional(readOnly = true)
    public List<PasswordRecoveryResponse> getAll() {
        return passwordRecoveryRepository.findAll()
                .stream()
                .map(passwordRecoveryMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PasswordRecoveryResponse getById(Long id) {
        PasswordRecovery passwordRecovery = passwordRecoveryRepository.findById(id)
                .orElseThrow(() -> new PasswordRecoveryNotFoundException(id));
        return passwordRecoveryMapper.mapToDto(passwordRecovery);
    }

    public void deleteById(Long id) {
        passwordRecoveryRepository.deleteById(id);
    }

    public void save(PasswordRecoveryRequest passwordRecoveryRequest) {
        String username = passwordRecoveryRequest.getUsername();
        User user = userRepository.findByUsername(username)
                .orElse(userRepository.findByEmail(username)
                        .orElseThrow(() -> new UserNotFoundException(username)));

        Long number =  (long) ((Math.random() * 900000) + 100000);
        PasswordRecovery passwordRecovery = PasswordRecovery.builder()
                .number(number)
                .user(user)
                .build();

        passwordRecoveryRepository.save(passwordRecovery);
    }

}
