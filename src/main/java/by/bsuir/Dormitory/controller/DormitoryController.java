package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.DormitoryInfoRequest;
import by.bsuir.Dormitory.dto.request.DormitoryRequest;
import by.bsuir.Dormitory.dto.response.DormitoryResponse;
import by.bsuir.Dormitory.service.DormitoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/dormitories/")
@AllArgsConstructor
public class DormitoryController {

    private final DormitoryService dormitoryService;

    @GetMapping("/")
    public ResponseEntity<List<DormitoryResponse>> getAllDormitories() {
        return status(HttpStatus.OK)
                .body(dormitoryService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<DormitoryResponse> getDormitory(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDormitory(@PathVariable Long id) {
        dormitoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveDormitory(@RequestBody DormitoryRequest dormitoryRequest) {
        dormitoryService.save(dormitoryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/free")
    public ResponseEntity<List<DormitoryResponse>> getAllFree() {
        return status(HttpStatus.OK)
                .body(dormitoryService.getAllFree());
    }

    @GetMapping("/current")
    public ResponseEntity<DormitoryResponse> getCurrent() {
        return status(HttpStatus.OK)
                .body(dormitoryService.getCurrent());
    }

    @PutMapping("/{id}/info")
    public ResponseEntity<DormitoryResponse> updateInfo(@RequestBody DormitoryInfoRequest dormitoryRequest,
                                                        @PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryService.updateInfo(id, dormitoryRequest));
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<DormitoryResponse> updateName(@RequestBody DormitoryInfoRequest dormitoryRequest,
                                                        @PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryService.updateName(id, dormitoryRequest));
    }

    @PutMapping("/{id}/address")
    public ResponseEntity<DormitoryResponse> updateAddress(@RequestBody DormitoryInfoRequest dormitoryRequest,
                                                        @PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryService.updateAddress(id, dormitoryRequest));
    }

    @PutMapping("/{id}/main-image")
    public ResponseEntity<DormitoryResponse> updateMainImage(@RequestBody DormitoryInfoRequest dormitoryRequest,
                                                           @PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryService.updateMainImage(id, dormitoryRequest));
    }


}
