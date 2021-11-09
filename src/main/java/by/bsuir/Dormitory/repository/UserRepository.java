package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.Room;
import by.bsuir.Dormitory.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findAllByRoleNot(User.Role role);

    List<User> findAllByRole(User.Role role);

    Optional<User> findByEmail(String email);

    void deleteByUsername(String username);

    long countByRoom(Room room);

    List<User> findAllByRoom(Room room);

    List<User> findAllByRoomNotNull();

    List<User> findAllByRoomNull();

    List<User> findAllByDormitoryAndRoomNull(Dormitory dormitory);
}
