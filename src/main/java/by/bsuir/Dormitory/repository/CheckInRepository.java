package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    long countByRoomAndActiveIsTrue(Room room);

    long countByDormitoryAndActiveIsTrue(Dormitory dormitory);

    List<CheckIn> getAllByDormitoryAndActiveIsTrue(Dormitory dormitory);

    long countByUser_GenderAndActiveIsTrue(User.Gender gender);

    List<CheckIn> getAllByUser_GenderAndActiveIsTrue(User.Gender gender);

    List<CheckIn> findAllByRoomAndActiveIsTrue(Room room);

    List<CheckIn> findAllByRoomNotNullAndActiveIsTrue();

    List<CheckIn> findAllByRoomNull();

    List<CheckIn> findAllByDormitoryAndRoomNull(Dormitory dormitory);

    List<CheckIn> findAllByDormitory(Dormitory dormitory);

    List<CheckIn> findAllByDormitoryAndActiveIsTrue(Dormitory dormitory);

    List<CheckIn> findAllByActive(Boolean active);

    Optional<CheckIn> findByUserAndActiveIsTrue(User user);

    Optional<CheckIn> findByApplication(Application application);
}
