package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.RoomRequest;
import by.bsuir.Dormitory.dto.request.UserRoomRequest;
import by.bsuir.Dormitory.dto.response.RoomResponse;
import by.bsuir.Dormitory.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/rooms/")
@AllArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @GetMapping("/")
    public ResponseEntity<List<RoomResponse>> getAllRooms() {
        return status(HttpStatus.OK)
                .body(roomService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RoomResponse> getRoom(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(roomService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveRoom(@RequestBody RoomRequest roomRequest) {
        roomService.save(roomRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/free-rooms/count")
    public ResponseEntity<Long> countFreeRooms() {
        return status(HttpStatus.OK)
                .body(roomService.countFreeRooms());
    }

    @GetMapping("/free-rooms/by-dormitory/{dormitoryId}/count")
    public ResponseEntity<Long> countFreeRoomsByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(roomService.countFreeRoomsByDormitory(dormitoryId));
    }

    @GetMapping("/free-places/count")
    public ResponseEntity<Long> countFreePlaces() {
        return status(HttpStatus.OK)
                .body(roomService.countFreePlaces());
    }

    @GetMapping("/free-places/by-dormitory/{dormitoryId}/count")
    public ResponseEntity<Long> countFreePlacesByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(roomService.countFreePlacesByDormitory(dormitoryId));
    }

    @GetMapping("/free-rooms")
    public ResponseEntity<List<RoomResponse>> getAllFreeRooms() {
        return status(HttpStatus.OK)
                .body(roomService.getAllFreeRooms());
    }

    @GetMapping("/free-rooms/by-dormitory/{dormitoryId}")
    public ResponseEntity<List<RoomResponse>> getAllFreeRoomsByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(roomService.getAllFreeRoomsByDormitory(dormitoryId));
    }

    @GetMapping("/free-rooms/by-gender/{genderName}")
    public ResponseEntity<List<RoomResponse>> getAllFreeRoomsByGender(@PathVariable String genderName) {
        return status(HttpStatus.OK)
                .body(roomService.getAllFreeRoomsByGender(genderName));
    }

    @GetMapping("/free-places/by-dormitory/{dormitoryId}/by-gender/{genderName}")
    public ResponseEntity<List<RoomResponse>> getAllFreeRoomsByGenderAndDormitory(@PathVariable Long dormitoryId,
                                                                                  @PathVariable String genderName) {
        return status(HttpStatus.OK)
                .body(roomService.getAllFreeRoomsByDormitoryAndGender(dormitoryId, genderName));
    }

    @GetMapping("/current")
    public ResponseEntity<List<RoomResponse>> getAllCurrent() {
        return status(HttpStatus.OK)
                .body(roomService.getAllCurrent());
    }

    @GetMapping("/current/free-places/count")
    public ResponseEntity<Long> countAllCurrentFreePlaces() {
        return status(HttpStatus.OK)
                .body(roomService.countAllCurrentFreePlaces());
    }

    @GetMapping("/current/places/count")
    public ResponseEntity<Long> countAllCurrentPlaces() {
        return status(HttpStatus.OK)
                .body(roomService.countAllCurrentPlaces());
    }

    @PostMapping("/student-room")
    public ResponseEntity<Void> saveStudentRoom(@RequestBody UserRoomRequest userRoomRequest) {
        roomService.saveStudentRoom(userRoomRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-number/{number}/by-dormitory/{dormitoryId}")
    public ResponseEntity<RoomResponse> getRoomByNumber(@PathVariable String number, @PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(roomService.getByNumberAndDormitory(number, dormitoryId));
    }
}
