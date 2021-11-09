package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.PasswordRecoveryRequest;
import by.bsuir.Dormitory.dto.response.PasswordRecoveryResponse;
import by.bsuir.Dormitory.service.PasswordRecoveryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/passwordRecoverys/")
@AllArgsConstructor
public class PasswordRecoveryController {

    private final PasswordRecoveryService passwordRecoveryService;

    @GetMapping("/")
    public ResponseEntity<List<PasswordRecoveryResponse>> getAllPasswordRecoverys() {
        return status(HttpStatus.OK)
                .body(passwordRecoveryService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<PasswordRecoveryResponse> getPasswordRecovery(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(passwordRecoveryService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePasswordRecovery(@PathVariable Long id) {
        passwordRecoveryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> savePasswordRecovery(@RequestBody PasswordRecoveryRequest passwordRecoveryRequest) {
        passwordRecoveryService.save(passwordRecoveryRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
