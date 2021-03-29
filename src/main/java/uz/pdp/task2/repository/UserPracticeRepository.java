package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task2.entity.UserPractice;

@Repository
public interface UserPracticeRepository extends JpaRepository<UserPractice, Integer> {


}
