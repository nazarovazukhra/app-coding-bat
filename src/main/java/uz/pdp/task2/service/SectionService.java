package uz.pdp.task2.service;

import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.repository.CourseRepository;
import uz.pdp.task2.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {

    final SectionRepository sectionRepository;
    final CourseRepository courseRepository;

    public SectionService(SectionRepository sectionRepository, CourseRepository courseRepository) {
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
    }


    /**
     * this method returns all sections
     *
     * @return List<Section>
     */
    public List<Section> get() {
        return sectionRepository.findAll();
    }


    /**
     * this method returns one section by given id
     *
     * @param id Integer
     * @return Section
     */
    public Section getById(Integer id) {
        Optional<Section> optionalSection = sectionRepository.findById(id);
        return optionalSection.orElse(null);
    }


    /**
     * this method adds new section
     *
     * @param sectionDto json object comes
     * @return Result
     */
    public Result add(SectionDto sectionDto) {

        Optional<Course> optionalCourse = courseRepository.findById(sectionDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new Result("Such course not found", false);

        boolean existsByName = sectionRepository.existsByName(sectionDto.getName());
        if (existsByName)
            return new Result("Such section already exists", false);


        Section section = new Section();
        section.setName(sectionDto.getName());
        section.setCourse(optionalCourse.get());
        sectionRepository.save(section);
        return new Result("Section added", true);
    }


    /**
     * this method edits one section by given id
     *
     * @param id         Integer
     * @param sectionDto json object comes
     * @return Result
     */
    public Result edit(Integer id, SectionDto sectionDto) {

        Optional<Section> optionalSection = sectionRepository.findById(id);
        if (!optionalSection.isPresent())
            return new Result("Such section not found", false);

        boolean exists = sectionRepository.existsByNameAndIdNot(sectionDto.getName(), id);
        if (exists)
            return new Result("Such section already exists", false);

        Optional<Course> optionalCourse = courseRepository.findById(sectionDto.getCourseId());
        if (!optionalCourse.isPresent())
            return new Result("Such course not found", false);

        Section section = optionalSection.get();
        section.setName(sectionDto.getName());
        section.setCourse(optionalCourse.get());
        sectionRepository.save(section);
        return new Result("Section edited", true);


    }


    /**
     * this method deletes one section by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = sectionRepository.existsById(id);
        if (!exists)
            return new Result("Such section not found", false);

        sectionRepository.deleteById(id);
        return new Result("Section deleted", true);
    }
}
