package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Comment;

import by.bsuir.Dormitory.model.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByDormitory(Dormitory dormitory);
}
