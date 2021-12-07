package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.RoomRequest;
import by.bsuir.Dormitory.dto.request.UserRoomRequest;
import by.bsuir.Dormitory.dto.response.RoomResponse;
import by.bsuir.Dormitory.exception.CheckInNotFoundException;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.exception.RoomNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.RoomMapper;
import by.bsuir.Dormitory.model.CheckIn;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.CheckInRepository;
import by.bsuir.Dormitory.repository.DormitoryRepository;
import by.bsuir.Dormitory.repository.RoomRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final CheckInRepository checkInRepository;
    private final DormitoryRepository dormitoryRepository;

    private final RoomMapper roomMapper;

    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<RoomResponse> getAll() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public RoomResponse getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RoomNotFoundException(id));
        return roomMapper.mapToDto(room);
    }

    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    public void save(RoomRequest roomRequest) {
        Room room = roomMapper.map(roomRequest);
        roomRepository.save(room);
    }

    public Long countFreeRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .count();
    }

    public Long countFreeRoomsByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .count();
    }

    public Long countFreePlacesByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return countFreePlacesByDormitory(dormitory);
    }

    public Long countFreePlacesByDormitory(Dormitory dormitory) {
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .mapToLong(Room::getPlaces)
                .sum() - checkInRepository.countByDormitoryAndActiveIsTrue(dormitory);
    }

    public Long countFreePlaces() {
        return roomRepository.findAll()
                .stream()
                .mapToLong(room -> room.getPlaces() - checkInRepository.countByRoomAndActiveIsTrue(room))
                .sum();
    }

    public List<RoomResponse> getAllFreeRoomsByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRoomsByGender(String genderName) {
        User.Gender gender = User.Gender.getGenderByType(genderName);
        return roomRepository.findAllByGenderIn(Arrays.asList(gender, User.Gender.DEFAULT))
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRoomsByDormitoryAndGender(Long dormitoryId, String genderName) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        User.Gender gender = User.Gender.getGenderByType(genderName);
        return roomRepository.findAllByDormitoryAndGenderIn(dormitory, Arrays.asList(gender, User.Gender.DEFAULT))
                .stream()
                .filter(room -> room.getPlaces() > checkInRepository.countByRoomAndActiveIsTrue(room))
                .map(roomMapper::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public List<RoomResponse> getAllCurrent() {
        User user = authService.getCurrentUser();
        Dormitory dormitory = dormitoryRepository.findByManager(user)
                .orElseThrow(() -> new DormitoryNotFoundException(user));

        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .map(roomMapper::mapToDto)
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public Long countAllCurrentFreePlaces() {
        User user = authService.getCurrentUser();
        Dormitory dormitory = dormitoryRepository.findByManager(user)
                .orElseThrow(() -> new DormitoryNotFoundException(user));

        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .mapToLong(room -> room.getPlaces() - checkInRepository.countByRoomAndActiveIsTrue(room))
                .sum();
    }

    @Transactional(readOnly = true)
    public Long countAllCurrentPlaces() {
        User user = authService.getCurrentUser();
        Dormitory dormitory = dormitoryRepository.findByManager(user)
                .orElseThrow(() -> new DormitoryNotFoundException(user));

        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .mapToLong(Room::getPlaces)
                .sum();
    }

    public void saveStudentRoom(UserRoomRequest request) {
        Room room = roomRepository.findById(request.getRoomId())
                .orElseThrow(() -> new RoomNotFoundException(request.getRoomId()));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(request.getUserId()));
        CheckIn checkIn = checkInRepository.findByUserAndActiveIsTrue(user)
                .orElseThrow(() -> new CheckInNotFoundException(user));

        Room previous = checkIn.getRoom();

        if (checkInRepository.countByRoomAndActiveIsTrue(room) == 0) {
            room.setGender(user.getGender());
            room = roomRepository.save(room);
        }
        checkIn.setRoom(room);
        checkInRepository.save(checkIn);

        if (previous != null) {
            if (checkInRepository.countByRoomAndActiveIsTrue(previous) == 0) {
                previous.setGender(User.Gender.DEFAULT);
                roomRepository.save(previous);
            }
        }
    }

    @Transactional(readOnly = true)
    public RoomResponse getByNumberAndDormitory(String number, Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        Room room = roomRepository.findByNumberAndDormitory(number, dormitory)
                .orElseThrow(() -> new RoomNotFoundException(number));
        return roomMapper.mapToDto(room);
    }
}
