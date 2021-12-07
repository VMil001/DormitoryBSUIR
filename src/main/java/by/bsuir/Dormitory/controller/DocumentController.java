package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.DocumentRequest;
import by.bsuir.Dormitory.dto.response.DocumentResponse;
import by.bsuir.Dormitory.service.DocumentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/documents/")
@AllArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/")
    public ResponseEntity<List<DocumentResponse>> getAllDocuments() {
        return status(HttpStatus.OK)
                .body(documentService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(documentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveDocument(@RequestBody DocumentRequest documentRequest) {
        documentService.save(documentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-application/{applicationId}")
    public ResponseEntity<List<DocumentResponse>> getAllByApplication(@PathVariable Long applicationId) {
        return status(HttpStatus.OK)
                .body(documentService.getAllByApplication(applicationId));
    }
}
