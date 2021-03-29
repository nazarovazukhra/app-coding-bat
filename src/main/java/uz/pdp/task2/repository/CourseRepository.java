package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task2.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    boolean existsByName(String name);
}
