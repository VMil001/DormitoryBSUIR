package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.PrivilegeRequest;
import by.bsuir.Dormitory.dto.response.PrivilegeResponse;
import by.bsuir.Dormitory.service.PrivilegeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/privileges/")
@AllArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    @GetMapping("/")
    public ResponseEntity<List<PrivilegeResponse>> getAllPrivileges() {
        return status(HttpStatus.OK)
                .body(privilegeService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<PrivilegeResponse> getPrivilege(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(privilegeService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegeService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> savePrivilege(@RequestBody PrivilegeRequest privilegeRequest) {
        privilegeService.save(privilegeRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-right/{rightId}")
    public ResponseEntity<List<PrivilegeResponse>> getAllByRight(@PathVariable Long rightId) {
        return status(HttpStatus.OK)
                .body(privilegeService.getAllByRight(rightId));
    }
}
