package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Document;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findAllByUser(User user);
}
