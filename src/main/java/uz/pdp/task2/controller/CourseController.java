package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Course;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.service.CourseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    /**
     * this method return all courses
     *
     * @return List<Course>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Course> courseList = courseService.get();
        return ResponseEntity.ok(courseList);
    }


    /**
     * this method return one course by id
     *
     * @param id Integer
     * @return Course
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Course course = courseService.getById(id);
        return ResponseEntity.ok(course);
    }

    /**
     * this method adds new course
     *
     * @param course json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody Course course) {
        Result result = courseService.add(course);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method edits one course by given id
     *
     * @param id     Integer
     * @param course json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody Course course) {
        Result result = courseService.edit(id, course);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }

    /**
     * this methods deletes one course by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = courseService.delete(id);
        return ResponseEntity.status(result.getSuccess() ? 200 : 409).body(result);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
