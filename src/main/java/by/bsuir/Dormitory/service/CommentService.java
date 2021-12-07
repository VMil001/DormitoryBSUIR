package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.CommentRequest;
import by.bsuir.Dormitory.dto.response.CommentResponse;
import by.bsuir.Dormitory.exception.CommentNotFoundException;
import by.bsuir.Dormitory.exception.DormitoryNotFoundException;
import by.bsuir.Dormitory.mapper.CommentMapper;
import by.bsuir.Dormitory.model.Comment;
import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.repository.CommentRepository;


import by.bsuir.Dormitory.repository.DormitoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final DormitoryRepository dormitoryRepository;

    private final CommentMapper commentMapper;

    @Transactional(readOnly = true)
    public List<CommentResponse> getAll() {
        return commentRepository.findAll()
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public CommentResponse getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));
        return commentMapper.mapToDto(comment);
    }

    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    public void save(CommentRequest commentRequest) {
        Comment comment = commentMapper.map(commentRequest);
        commentRepository.save(comment);
    }

    public List<CommentResponse> getAllByDormitory(Long dormitoryId) {
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId)
                .orElseThrow(() -> new DormitoryNotFoundException(dormitoryId));
        return commentRepository.findAllByDormitoryOrderByDateDesc(dormitory)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
