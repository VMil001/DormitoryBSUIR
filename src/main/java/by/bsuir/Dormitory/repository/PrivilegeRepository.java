package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Privilege;
import by.bsuir.Dormitory.model.Right;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    List<Privilege> findAllByRight(Right right);
}
