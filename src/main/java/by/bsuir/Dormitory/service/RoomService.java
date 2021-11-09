package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.RoomRequest;
import by.bsuir.Dormitory.dto.response.RoomResponse;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.exception.RoomNotFoundException;
import by.bsuir.Dormitory.mapper.RoomMapper;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.model.User;
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
    private final DormitoryRepository dormitoryRepository;

    private final RoomMapper roomMapper;

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
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .count();
    }

    public Long countFreeRoomsByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .count();
    }

    public Long countFreePlacesByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .mapToLong(room -> room.getPlaces() - userRepository.findAllByRoom(room).size())
                .sum();
    }

    public Long countFreePlaces() {
        return roomRepository.findAll()
                .stream()
                .mapToLong(room -> room.getPlaces() - userRepository.findAllByRoom(room).size())
                .sum();
    }

    public List<RoomResponse> getAllFreeRoomsByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return roomRepository.findAllByDormitory(dormitory)
                .stream()
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRooms() {
        return roomRepository.findAll()
                .stream()
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRoomsByGender(String genderName) {
        User.Gender gender = User.Gender.getGenderByType(genderName);
        return roomRepository.findAllByGenderIn(Arrays.asList(gender, User.Gender.DEFAULT))
                .stream()
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public List<RoomResponse> getAllFreeRoomsByDormitoryAndGender(Long dormitoryId, String genderName) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        User.Gender gender = User.Gender.getGenderByType(genderName);
        return roomRepository.findAllByGenderInAndDormitory(Arrays.asList(gender, User.Gender.DEFAULT), dormitory)
                .stream()
                .filter(room -> room.getPlaces() > userRepository.findAllByRoom(room).size())
                .map(roomMapper::mapToDto)
                .collect(toList());
    }

    public Boolean isRoomPresent(String number) {
        return roomRepository.findByNumber(number).isPresent();
    }

}
