package uz.pdp.task2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.task2.entity.Section;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {

    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
