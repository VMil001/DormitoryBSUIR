package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.*;
import by.bsuir.Dormitory.dto.response.UserResponse;
import by.bsuir.Dormitory.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/users/")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return status(HttpStatus.OK)
                .body(userService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(userService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/by-username/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable Long id) {
        userService.blockById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable Long id) {
        userService.unblockById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-username/{username}")
    public ResponseEntity<UserResponse> getUser(@PathVariable String username) {
        return status(HttpStatus.OK)
                .body(userService.getByUsername(username));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserInfoRequest userRequest, @PathVariable Long userId) {
        return status(HttpStatus.OK)
                .body(userService.update(userId, userRequest));
    }

    @PutMapping("/by-username/{username}")
    public ResponseEntity<UserResponse> updateUser(@RequestBody UserInfoRequest userRequest,
                                                   @PathVariable String username) {
        return status(HttpStatus.OK)
                .body(userService.update(username, userRequest));
    }

    @PutMapping("/students/{userId}")
    public ResponseEntity<UserResponse> updateStudent(@RequestBody StudentInfoRequest studentInfoRequest,
                                                      @PathVariable Long userId) {
        return status(HttpStatus.OK)
                .body(userService.update(userId, studentInfoRequest));
    }

    @PutMapping("/students/by-username/{username}")
    public ResponseEntity<UserResponse> updateStudent(@RequestBody StudentInfoRequest studentInfoRequest,
                                                      @PathVariable String username) {
        return status(HttpStatus.OK)
                .body(userService.update(username, studentInfoRequest));
    }

    @GetMapping("/xml")
    public ResponseEntity<Void> saveXMl() {
        userService.saveToXml();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<UserResponse>> getAllStudents() {
        return status(HttpStatus.OK)
                .body(userService.getAllStudents());
    }

    @GetMapping("/managers")
    public ResponseEntity<List<UserResponse>> getAllManagers() {
        return status(HttpStatus.OK)
                .body(userService.getAllManagers());
    }

    @GetMapping("/students/with-room")
    public ResponseEntity<List<UserResponse>> getAllStudentsWithRoom() {
        return status(HttpStatus.OK)
                .body(userService.getAllStudentsWithRoom());
    }

    @GetMapping("/students/by-dormitory/{dormitoryId}/without-room")
    public ResponseEntity<List<UserResponse>> getAllStudentsWithoutRoomByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(userService.getAllStudentsWithoutRoomByDormitory(dormitoryId));
    }


    @GetMapping("/students/by-dormitory/{dormitoryId}")
    public ResponseEntity<List<UserResponse>> getAllStudentsByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(userService.getAllStudentsByDormitory(dormitoryId));
    }

    @GetMapping("/students/without-dormitory/")
    public ResponseEntity<List<UserResponse>> getAllStudentsWithoutDormitory() {
        return status(HttpStatus.OK)
                .body(userService.getAllStudentsWithoutDormitory());
    }

    @GetMapping("/students/queue/")
    public ResponseEntity<List<UserResponse>> getStudentsQueue() {
        return status(HttpStatus.OK)
                .body(userService.getStudentsQueue());
    }

    @GetMapping("/current")
    public ResponseEntity<UserResponse> getCurrent() {
        return status(HttpStatus.OK)
                .body(userService.getCurrent());
    }

    @PutMapping("/{userId}/update-part/imgURL")
    public ResponseEntity<UserResponse> updateImage(@PathVariable Long userId, @RequestBody UpdatePartRequest request) {
        return status(HttpStatus.OK)
                .body(userService.updateImage(userId, request));
    }

    @PutMapping("/{userId}/update-part/course")
    public ResponseEntity<UserResponse> updateCourse(@PathVariable Long userId, @RequestBody UpdatePartRequest request) {
        return status(HttpStatus.OK)
                .body(userService.updateCourse(userId, request));
    }

    @PutMapping("/{userId}/update-part/faculty")
    public ResponseEntity<UserResponse> updateFaculty(@PathVariable Long userId, @RequestBody UpdatePartRequest request) {
        return status(HttpStatus.OK)
                .body(userService.updateFaculty(userId, request));
    }

    @PutMapping("/{userId}/update-part/group")
    public ResponseEntity<UserResponse> updateGroup(@PathVariable Long userId, @RequestBody UpdatePartRequest request) {
        return status(HttpStatus.OK)
                .body(userService.updateGroup(userId, request));
    }

}
