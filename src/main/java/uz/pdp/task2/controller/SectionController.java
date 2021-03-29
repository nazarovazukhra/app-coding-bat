package uz.pdp.task2.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.payload.SectionDto;
import uz.pdp.task2.service.SectionService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/section")
public class SectionController {

    final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }


    /**
     * this method returns all sections
     *
     * @return List<Section>
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Section> sectionList = sectionService.get();
        return ResponseEntity.ok(sectionList);
    }


    /**
     * this method returns one section by given id
     *
     * @param id Integer
     * @return Section
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        Section section = sectionService.getById(id);
        return ResponseEntity.ok(section);
    }


    /**
     * this method adds new section
     *
     * @param sectionDto json object comes
     * @return Result
     */
    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody SectionDto sectionDto) {
        Result result = sectionService.add(sectionDto);
        return ResponseEntity.status(result.getSuccess() ? 201 : 409).body(result);
    }


    /**
     * this method edits one section by given id
     *
     * @param id         Integer
     * @param sectionDto json object comes
     * @return Result
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable Integer id, @Valid @RequestBody SectionDto sectionDto) {
        Result result = sectionService.edit(id, sectionDto);
        return ResponseEntity.status(result.getSuccess() ? 202 : 409).body(result);
    }


    /**
     * this method deletes one section by given id
     *
     * @param id Integer
     * @return Result
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        Result result = sectionService.delete(id);
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
