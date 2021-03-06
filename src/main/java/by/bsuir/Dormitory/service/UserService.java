package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.*;
import by.bsuir.Dormitory.dto.response.UserResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.exception.RoomNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.UserMapper;
import by.bsuir.Dormitory.model.*;
import by.bsuir.Dormitory.repository.*;
import by.bsuir.Dormitory.service.xml.DOMXmlFactory;
import by.bsuir.Dormitory.service.xml.XMLFactory;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final CheckInRepository checkInRepository;
    private final ApplicationRepository applicationRepository;
    private final DormitoryRepository dormitoryRepository;
    private final RoomRepository roomRepository;

    private final AuthService authService;

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UserResponse> getAll() {
        return userRepository.findAllByRoleNot(User.Role.ADMIN)
                .stream()
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudents() {
        return userRepository.findAllByRole(User.Role.STUDENT)
                .stream()
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllManagers() {
        return userRepository.findAllByRole(User.Role.MANAGER)
                .stream()
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return userMapper.mapToDto(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public void blockById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(false);
        userRepository.save(user);
    }

    public void unblockById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setActive(true);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserResponse getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return userMapper.mapToDto(user);
    }

    public UserResponse update(Long userId, UserInfoRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return update(userRequest, user);
    }

    public UserResponse update(String username, UserInfoRequest userRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return update(userRequest, user);
    }

    private UserResponse update(UserInfoRequest userRequest, User user) {
        user.setLastName(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setPatronymic(userRequest.getPatronymic());
        user.setAddress(userRequest.getAddress());
        user.setPhone(userRequest.getPhone());
        user.setGender(User.Gender.getGenderByType(userRequest.getGender()));
        return userMapper.mapToDto(user);
    }

    public UserResponse update(Long userId, StudentInfoRequest userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return update(userRequest, user);
    }

    public UserResponse update(String username, StudentInfoRequest userRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return update(userRequest, user);
    }

    private UserResponse update(StudentInfoRequest studentInfoRequest, User user) {
        user.setFaculty(studentInfoRequest.getFaculty());
        user.setGroupNumber(studentInfoRequest.getGroupNumber());
        user.setCourse(studentInfoRequest.getCourse());
        return userMapper.mapToDto(user);
    }

//    public UserResponse updateUserDormitory(Long userId, UserDormitoryRequest request) {
//        Dormitory dormitory = dormitoryRepository.findById(request.getDormitoryId())
//                .orElseThrow(() -> new DormitoryNotFoundException(request.getDormitoryId()));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//        user.setDormitory(dormitory);
//        user = userRepository.save(user);
//        return userMapper.mapToDto(user);
//    }
//
//    public UserResponse updateUserRoom(Long userId, UserRoomRequest request) {
//        Room room = roomRepository.findById(request.getRoomId())
//                .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new UserNotFoundException(userId));
//        user.setRoom(room);
//        user = userRepository.save(user);
//        return userMapper.mapToDto(user);
//    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudentsWithRoom() {
        return checkInRepository.findAllByRoomNotNullAndActiveIsTrue()
                .stream()
                .map(CheckIn::getUser)
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudentsWithoutRoomByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));

        return checkInRepository.findAllByDormitoryAndRoomNull(dormitory)
                .stream()
                .map(CheckIn::getUser)
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudentsByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return checkInRepository.findAllByDormitoryAndActiveIsTrue(dormitory)
                .stream()
                .map(CheckIn::getUser)
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getAllStudentsWithoutDormitory() {
//        return applicationRepository.findAllByStatusNot(Application.Status.CONFIRMED)
//                .stream()
//                .map(Application::getUser)
//                .map(userMapper::mapToDto)
//                .collect(toList());
        return getAll();
    }

    @Transactional(readOnly = true)
    public List<UserResponse> getStudentsQueue() {
        return applicationRepository.findAllByStatus(Application.Status.WAITING)
                .stream()
                .map(Application::getUser)
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public void saveToXml() {
        List<User> users = userRepository.findAll();
        String filename = "D:\\Polina\\??????????????????\\????????????\\??????\\????????????\\users.xml";
        XMLFactory xmlFactory = new DOMXmlFactory(filename);
        try {
            xmlFactory.saveUsers(users);
        } catch (ParserConfigurationException | SAXException | IOException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrent() {
        return userMapper.mapToDto(authService.getCurrentUser());
    }

    public UserResponse updateImage(Long id, UpdatePartRequest request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(id));
        user.setImgURL(request.getPart());
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public UserResponse updateCourse(Long id, UpdatePartRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setCourse(Integer.parseInt(request.getPart()));
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public UserResponse updateFaculty(Long id, UpdatePartRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setFaculty(request.getPart());
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

    public UserResponse updateGroup(Long id, UpdatePartRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setGroupNumber(request.getPart());
        user = userRepository.save(user);
        return userMapper.mapToDto(user);
    }

}
