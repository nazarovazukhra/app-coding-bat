package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.payload.ProblemDto;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.service.ProblemService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problem")
public class ProblemController {

    final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }


    /**
     * this method adds new problem
     *
     * @param problemDto json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProblemDto problemDto) {
        Result result = problemService.add(problemDto);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method returns all problems
     *
     * @return List<Problem>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Problem> problemList = problemService.get();
        return ResponseEntity.ok(problemList);
    }


    /**
     * this method returns one problem by given id
     *
     * @param id Integer
     * @return Problem
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Problem problem = problemService.getById(id);
        return ResponseEntity.ok(problem);
    }


    /**
     * this method edits one problem by given id
     *
     * @param id         Integer
     * @param problemDto json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody ProblemDto problemDto) {
        Result result = problemService.edit(id, problemDto);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }

    /**
     * this methods deletes one problem by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = problemService.delete(id);
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
