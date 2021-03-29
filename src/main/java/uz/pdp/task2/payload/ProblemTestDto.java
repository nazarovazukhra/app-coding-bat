package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemTestDto {

    @NotNull(message = "arguments id must not be null")
    private String arguments;

    @NotNull(message = "result id must not be null")
    private String result;

    @NotNull(message = "problem id must not be null")
    private Integer problemId;
}
