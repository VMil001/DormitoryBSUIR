package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.CheckInRequest;
import by.bsuir.Dormitory.dto.response.CheckInResponse;
import by.bsuir.Dormitory.exception.CheckInNotFoundException;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.CheckInMapper;
import by.bsuir.Dormitory.model.CheckIn;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.NotificationEmail;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.CheckInRepository;


import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CheckInService {

    private final CheckInRepository checkInRepository;
    private final DormitoryRepository dormitoryRepository;
    private final UserRepository userRepository;

    private final CheckInMapper checkInMapper;

    private final MailService mailService;

    @Transactional(readOnly = true)
    public List<CheckInResponse> getAll() {
        return checkInRepository.findAll()
                .stream()
                .map(checkInMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public CheckInResponse getById(Long id) {
        CheckIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new CheckInNotFoundException(id));
        return checkInMapper.mapToDto(checkIn);
    }

    public void deleteById(Long id) {
        checkInRepository.deleteById(id);
    }

    public void save(CheckInRequest checkInRequest) {
        CheckIn checkIn = checkInMapper.map(checkInRequest);
        checkInRepository.save(checkIn);



        NotificationEmail notificationEmail = new NotificationEmail("Заселение в общежитие",
                checkIn.getUser().getEmail(),
                "Здравствуйте! Вам выделено место в " + checkIn.getDormitory().getName() +
                " по адресу " + checkIn.getDormitory().getAddress() +
                ". Можно ехать заселяться. Для уточнения информации обратитесь, " +
                        "пожалуйста, в 311-2 кабинет, по телефону 8-017-2930-23-59 или " +
                        "в любом удобном для Вас мессенджере. С уважением, Ваш профком");

        mailService.sendMail(notificationEmail);
    }

    @Transactional(readOnly = true)
    public List<CheckInResponse> getAllByActive(Boolean active) {
        return checkInRepository.findAllByActive(active)
                .stream()
                .map(checkInMapper::mapToDto)
                .collect(toList());
    }

    public void evictById(Long id) {
        CheckIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new CheckInNotFoundException(id));
        checkIn.setActive(false);
        checkIn.setDateOut(new Date());
        checkInRepository.save(checkIn);

        NotificationEmail notificationEmail = new NotificationEmail("Выселение из общежития",
                checkIn.getUser().getEmail(),
                "Здравствуйте! Вы были выселены из " + checkIn.getDormitory().getName() +
                        " по адресу " + checkIn.getDormitory().getAddress() +
                        ". Для уточнения информации обратитесь, " +
                        "пожалуйста, в 311-2 кабинет, по телефону 8-017-2930-23-59 или " +
                        "в любом удобном для Вас мессенджере. С уважением, Ваш профком");
        mailService.sendMail(notificationEmail);
    }

    public void sendMessageById(Long id) {
        CheckIn checkIn = checkInRepository.findById(id)
                .orElseThrow(() -> new CheckInNotFoundException(id));

        NotificationEmail notificationEmail = new NotificationEmail("Заселение в общежитие",
                checkIn.getUser().getEmail(),
                "Здравствуйте! Вам выделено место в " + checkIn.getDormitory().getName() +
                        " по адресу " + checkIn.getDormitory().getAddress() +
                        ". Можно ехать заселяться. Для уточнения информации обратитесь, " +
                        "пожалуйста, в 311-2 кабинет, по телефону 8-017-2930-23-59 или " +
                        "в любом удобном для Вас мессенджере. С уважением, Ваш профком");
        mailService.sendMail(notificationEmail);
    }


    @Transactional(readOnly = true)
    public Long countActiveByDormitory(Long id) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));

        return checkInRepository.countByDormitoryAndActiveIsTrue(dormitory);
    }

    @Transactional(readOnly = true)
    public List<CheckInResponse> getAllActiveByDormitory(Long id) {
        Dormitory dormitory = dormitoryRepository.findById(id)
                .orElseThrow(() -> new DormitoryNotFoundException(id));

        return checkInRepository.getAllByDormitoryAndActiveIsTrue(dormitory)
                .stream()
                .map(checkInMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Long countActiveByGender(String type) {
        User.Gender gender = User.Gender.getGenderByType(type);

        return checkInRepository.countByUser_GenderAndActiveIsTrue(gender);
    }

    @Transactional(readOnly = true)
    public List<CheckInResponse> getAllActiveByGender(String type) {
        User.Gender gender = User.Gender.getGenderByType(type);

        return checkInRepository.getAllByUser_GenderAndActiveIsTrue(gender)
                .stream()
                .map(checkInMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public CheckInResponse getActiveByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return checkInMapper.mapToDto(checkInRepository.findByUserAndActiveIsTrue(user)
                .orElseThrow(() -> new CheckInNotFoundException(user)));
    }
}
