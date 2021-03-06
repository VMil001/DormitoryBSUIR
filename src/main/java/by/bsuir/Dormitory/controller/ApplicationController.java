package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.ApplicationRequest;
import by.bsuir.Dormitory.dto.request.ApplicationStatusRequest;
import by.bsuir.Dormitory.dto.response.ApplicationResponse;
import by.bsuir.Dormitory.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/applications/")
@AllArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping("/")
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {
        return status(HttpStatus.OK)
                .body(applicationService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<ApplicationResponse> getApplication(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(applicationService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<ApplicationResponse> saveApplication(@RequestBody ApplicationRequest applicationRequest) {
        return status(HttpStatus.OK)
                .body(applicationService.save(applicationRequest));
    }

    @PutMapping("/status")
    public ResponseEntity<Void> changeStatus(@RequestBody ApplicationStatusRequest request) {
        applicationService.changeStatus(request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/current-user")
    public ResponseEntity<ApplicationResponse> getCurrentUserApplication() {
        return status(HttpStatus.OK)
                .body(applicationService.getByCurrentUser());
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ApplicationResponse>> getAllByUser(@PathVariable Long userId) {
        return status(HttpStatus.OK)
                .body(applicationService.getAllByUser(userId));
    }

    @GetMapping("/by-current-user")
    public ResponseEntity<List<ApplicationResponse>> getAllByCurrentUser() {
        return status(HttpStatus.OK)
                .body(applicationService.getAllByCurrentUser());
    }

    @GetMapping("/by-status/{type}")
    public ResponseEntity<List<ApplicationResponse>> getAllByStatus(@PathVariable String type) {
        return status(HttpStatus.OK)
                .body(applicationService.getAllByStatus(type));
    }

    @GetMapping("/by-status/{type}/count")
    public ResponseEntity<Long> countByStatus(@PathVariable String type) {
        return status(HttpStatus.OK)
                .body(applicationService.countByStatus(type));
    }

}
