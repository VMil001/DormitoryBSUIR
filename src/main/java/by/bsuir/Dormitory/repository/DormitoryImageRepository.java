package by.bsuir.Dormitory.repository;

import by.bsuir.Dormitory.model.Dormitory;
import by.bsuir.Dormitory.model.DormitoryImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DormitoryImageRepository extends JpaRepository<DormitoryImage, Long> {

    List<DormitoryImage> findAllByDormitory(Dormitory dormitory);
}
