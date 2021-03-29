package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.ProblemTest;
import uz.pdp.task2.payload.ProblemTestDto;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.service.ProblemTestService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problemTest")
public class ProblemTestController {

    final ProblemTestService problemTestService;

    public ProblemTestController(ProblemTestService problemTestService) {
        this.problemTestService = problemTestService;
    }


    /**
     * this method returns all problemTest
     *
     * @return List<ProblemTest>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<ProblemTest> problemTestList = problemTestService.get();
        return ResponseEntity.ok(problemTestList);
    }


    /**
     * this method returns one problemTest by given id
     *
     * @param id Integer
     * @return ProblemTest
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        ProblemTest problemTest = problemTestService.getById(id);
        return ResponseEntity.ok(problemTest);
    }


    /**
     * this method deletes one problemTest by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = problemTestService.delete(id);
        return ResponseEntity.status(result.getSuccess() ? 200 : 409).body(result);
    }


    /**
     * this method adds new problemTest
     *
     * @param problemTestDto json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProblemTestDto problemTestDto) {
        Result result = problemTestService.add(problemTestDto);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);

    }


    /**
     * this method edits one problemTest by given id
     *
     * @param id             Integer
     * @param problemTestDto json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody ProblemTestDto problemTestDto) {
        Result result = problemTestService.edit(id, problemTestDto);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);

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
