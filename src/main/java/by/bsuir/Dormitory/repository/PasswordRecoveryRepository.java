package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.PasswordRecovery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecovery, Long> {
}
