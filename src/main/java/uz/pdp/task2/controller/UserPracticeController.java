package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.UserPractice;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.payload.UserPracticeDto;
import uz.pdp.task2.service.UserPracticeService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/userPractice")
public class UserPracticeController {

    final UserPracticeService userPracticeService;

    public UserPracticeController(UserPracticeService userPracticeService) {
        this.userPracticeService = userPracticeService;
    }


    /**
     * this method returns all userPractice
     *
     * @return List<UserPractice>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserPractice> userPracticeList = userPracticeService.get();
        return ResponseEntity.ok(userPracticeList);
    }


    /**
     * this method returns one userPractice by given id
     *
     * @param id Integer
     * @return UserPractice
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        UserPractice userPractice = userPracticeService.getById(id);
        return ResponseEntity.ok(userPractice);
    }


    /**
     * this method deletes one userPractice by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = userPracticeService.deleted(id);
        return ResponseEntity.status(result.getSuccess() ? 200 : 409).body(result);
    }


    /**
     * this method adds new userPractice
     *
     * @param userPracticeDto json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody UserPracticeDto userPracticeDto) {
        Result result = userPracticeService.add(userPracticeDto);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);

    }


    /**
     * this method edits one userPractice by given id
     *
     * @param id              Integer
     * @param userPracticeDto json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody UserPracticeDto userPracticeDto) {
        Result result = userPracticeService.edit(id, userPracticeDto);
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
