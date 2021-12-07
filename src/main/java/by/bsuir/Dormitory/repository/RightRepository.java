package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RightRepository extends JpaRepository<Right, Long> {
    Optional<Right> findByName(String name);
}
