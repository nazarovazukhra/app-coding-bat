package uz.pdp.task2.service;

import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    /**
     * this method return all courses
     *
     * @return List<Course>
     */
    public List<Course> get() {
        return courseRepository.findAll();
    }


    /**
     * this method return one course by id
     *
     * @param id Integer
     * @return Course
     */
    public Course getById(Integer id) {

        Optional<Course> optionalCourse = courseRepository.findById(id);
        return optionalCourse.orElse(null);

    }


    /**
     * this method adds new course
     *
     * @param course json object comes
     * @return Result
     */
    public Result add(Course course) {

        boolean existsByName = courseRepository.existsByName(course.getName());
        if (existsByName)
            return new Result("Such course already exists", false);

        Course newCourse = new Course();
        newCourse.setName(course.getName());
        newCourse.setDescription(course.getDescription());
        courseRepository.save(newCourse);
        return new Result("Course added", true);
    }


    /**
     * this method edits one course by given id
     *
     * @param id     Integer
     * @param course json object comes
     * @return Result
     */
    public Result edit(Integer id, Course course) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (!optionalCourse.isPresent())
            return new Result("Such course not found", false);

        Course editing = optionalCourse.get();
        editing.setName(course.getName());
        editing.setDescription(course.getDescription());
        courseRepository.save(editing);
        return new Result("Course edited", true);
    }


    /**
     * this methods deletes one course by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = courseRepository.existsById(id);
        if (!exists)
            return new Result("Such course not found", false);
        courseRepository.deleteById(id);
        return new Result("Course deleted", true);
    }


}
