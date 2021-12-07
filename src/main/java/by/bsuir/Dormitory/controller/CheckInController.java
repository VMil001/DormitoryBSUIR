package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.CheckInRequest;
import by.bsuir.Dormitory.dto.response.CheckInResponse;
import by.bsuir.Dormitory.service.CheckInService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/check-ins/")
@AllArgsConstructor
public class CheckInController {

    private final CheckInService checkInService;

    @GetMapping("/")
    public ResponseEntity<List<CheckInResponse>> getAllCheckIns() {
        return status(HttpStatus.OK)
                .body(checkInService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CheckInResponse> getCheckIn(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(checkInService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCheckIn(@PathVariable Long id) {
        checkInService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveCheckIn(@RequestBody CheckInRequest checkInRequest) {
        checkInService.save(checkInRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-active/{active}")
    public ResponseEntity<List<CheckInResponse>> getAllByActive(@PathVariable Boolean active) {
        return status(HttpStatus.OK)
                .body(checkInService.getAllByActive(active));
    }

    @GetMapping("/evict/{id}")
    public ResponseEntity<Void> evictById(@PathVariable Long id) {
        checkInService.evictById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/send-message/{id}")
    public ResponseEntity<Void> sendMessageById(@PathVariable Long id) {
        checkInService.sendMessageById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/active/by-dormitory/{id}/count")
    public ResponseEntity<Long> countActiveByDormitory(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(checkInService.countActiveByDormitory(id));
    }

    @GetMapping("/active/by-user/{userId}")
    public ResponseEntity<CheckInResponse> getActiveByUser(@PathVariable Long userId) {
        return status(HttpStatus.OK)
                .body(checkInService.getActiveByUser(userId));
    }

    @GetMapping("/active/by-gender/{gender}/count")
    public ResponseEntity<Long> countActiveByGender(@PathVariable String gender) {
        return status(HttpStatus.OK)
                .body(checkInService.countActiveByGender(gender));
    }

    @GetMapping("/active/by-gender/{gender}")
    public ResponseEntity<List<CheckInResponse>> getAllActiveByGender(@PathVariable String gender) {
        return status(HttpStatus.OK)
                .body(checkInService.getAllActiveByGender(gender));
    }

    @GetMapping("/active/by-dormitory/{id}")
    public ResponseEntity<List<CheckInResponse>> getAllActiveByDormitory(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(checkInService.getAllActiveByDormitory(id));
    }
}
