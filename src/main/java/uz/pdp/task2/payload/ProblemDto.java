package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemDto {

    @NotNull(message = "title must not be null")
    private String title;

    @NotNull(message = "body must not be null")
    private String body;

    @NotNull(message = "section id id must not be null")
    private Integer sectionId;
}
