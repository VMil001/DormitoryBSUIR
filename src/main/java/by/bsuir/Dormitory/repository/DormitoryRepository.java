package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {
}
