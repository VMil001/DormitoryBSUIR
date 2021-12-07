package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DormitoryRepository extends JpaRepository<Dormitory, Long> {
    Optional<Dormitory> findByManager(User manager);

    Optional<Dormitory> findByName(String name);
}
