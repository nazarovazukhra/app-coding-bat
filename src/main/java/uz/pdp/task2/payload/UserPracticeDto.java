package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPracticeDto {


    @NotNull(message = "user id id must not be null")
    private Integer userId;

    @NotNull(message = "problem id id must not be null")
    private Integer problemId;

    @NotNull(message = "userSolution must not be null")
    private String userSolution;

    @NotNull(message = "score must not be null")
    private Integer score;

}
