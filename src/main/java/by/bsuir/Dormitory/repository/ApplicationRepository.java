package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    Long countByStatus(Application.Status status);

    List<Application> findAllByStatus(Application.Status status);

    List<Application> findAllByStatusNot(Application.Status status);

    List<Application> findAllByStatusIn(List<Application.Status> statuses);

    List<Application> findAllByStatusNotIn(List<Application.Status> statuses);

    List<Application> findAllByUserOrderByDateDesc(User user);

    List<Application> findAllByStatusOrderByDate(Application.Status status);

    List<Application> findAllByStatusOrderByNumber(Application.Status status);

    Optional<Application> findFirstByUserOrderByDateDesc(User user);
}
