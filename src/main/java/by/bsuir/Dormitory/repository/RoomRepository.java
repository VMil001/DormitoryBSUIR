package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByDormitory(Dormitory dormitory);

    List<Room> findAllByGenderIn(List<User.Gender> asList);

    List<Room> findAllByGenderInAndDormitory(List<User.Gender> asList, Dormitory dormitory);

    Optional<Room> findByNumber(String name);
}
