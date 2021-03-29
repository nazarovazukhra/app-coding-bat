package uz.pdp.task2.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDto {

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "course id must not be null")
    private Integer courseId;
}
