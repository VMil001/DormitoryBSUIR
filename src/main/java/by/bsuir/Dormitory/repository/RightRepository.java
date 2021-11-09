package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightRepository extends JpaRepository<Right, Long> {
}
