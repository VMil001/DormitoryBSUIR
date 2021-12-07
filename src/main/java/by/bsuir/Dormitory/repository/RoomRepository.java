package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByDormitory(Dormitory dormitory);

    List<Room> findAllByGenderIn(List<User.Gender> asList);

    List<Room> findAllByDormitoryAndGenderIn(Dormitory dormitory, List<User.Gender> genders);

    Optional<Room> findByNumberAndDormitory(String name, Dormitory dormitory);

    @Query("""
            SELECT SUM(r.places)
            FROM Room r
            WHERE r.dormitory = :dormitory
            """)
    Long countPlacesByDormitory(Dormitory dormitory);
}
