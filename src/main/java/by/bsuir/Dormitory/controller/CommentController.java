package by.bsuir.Dormitory.controller;

import by.bsuir.Dormitory.dto.request.CommentRequest;
import by.bsuir.Dormitory.dto.response.CommentResponse;
import by.bsuir.Dormitory.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/comments/")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<List<CommentResponse>> getAllComments() {
        return status(HttpStatus.OK)
                .body(commentService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long id) {
        return status(HttpStatus.OK)
                .body(commentService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveComment(@RequestBody CommentRequest commentRequest) {
        commentService.save(commentRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/by-dormitory/{dormitoryId}")
    public ResponseEntity<List<CommentResponse>> getAllCommentsByDormitory(@PathVariable Long dormitoryId) {
        return status(HttpStatus.OK)
                .body(commentService.getAllByDormitory(dormitoryId));
    }

}
