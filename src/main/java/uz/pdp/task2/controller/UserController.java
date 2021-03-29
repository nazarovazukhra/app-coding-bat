package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.service.UserService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * this method returns all users
     *
     * @return List<User>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> userList = userService.get();
        return ResponseEntity.ok(userList);
    }


    /**
     * this method returns one user by given id
     *
     * @param id Integer
     * @return User
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }


    /**
     * this method adds new user
     *
     * @param user json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody User user) {
        Result result = userService.add(user);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method edits one user by given id
     *
     * @param id   Integer
     * @param user json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody User user) {
        Result result = userService.edit(id, user);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }


    /**
     * this method deletes one user by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = userService.delete(id);
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
