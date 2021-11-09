package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.RightRequest;
import by.bsuir.Dormitory.dto.response.RightResponse;
import by.bsuir.Dormitory.service.RightService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/rights/")
@AllArgsConstructor
public class RightController {

    private final RightService rightService;

    @GetMapping("/")
    public ResponseEntity<List<RightResponse>> getAllRights() {
        return status(HttpStatus.OK)
                .body(rightService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<RightResponse> getRight(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(rightService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRight(@PathVariable Long id) {
        rightService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveRight(@RequestBody RightRequest rightRequest) {
        rightService.save(rightRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
