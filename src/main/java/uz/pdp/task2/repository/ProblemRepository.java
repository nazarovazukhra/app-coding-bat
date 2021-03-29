package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task2.entity.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

}
