package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.DormitoryImageRequest;
import by.bsuir.Dormitory.dto.response.DormitoryImageResponse;
import by.bsuir.Dormitory.service.DormitoryImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/dormitory-images/")
@AllArgsConstructor
public class DormitoryImageController {

    private final DormitoryImageService dormitoryImageService;

    @GetMapping("/")
    public ResponseEntity<List<DormitoryImageResponse>> getAllDormitoryImages() {
        return status(HttpStatus.OK)
                .body(dormitoryImageService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<DormitoryImageResponse> getDormitoryImage(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(dormitoryImageService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDormitoryImage(@PathVariable Long id) {
        dormitoryImageService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveDormitoryImage(@RequestBody DormitoryImageRequest dormitoryImageRequest) {
        dormitoryImageService.save(dormitoryImageRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-dormitory/{dormitoryId}")
    public ResponseEntity<List<DormitoryImageResponse>> getAllImagesByDormitoryId(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(dormitoryImageService.getAllByDormitory(dormitoryId));
    }
}
