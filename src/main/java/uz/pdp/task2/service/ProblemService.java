package uz.pdp.task2.service;

import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.entity.Section;
import uz.pdp.task2.payload.ProblemDto;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.repository.ProblemRepository;
import uz.pdp.task2.repository.SectionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    final ProblemRepository problemRepository;
    final SectionRepository sectionRepository;

    public ProblemService(ProblemRepository problemRepository, SectionRepository sectionRepository) {
        this.problemRepository = problemRepository;
        this.sectionRepository = sectionRepository;
    }


    /**
     * this method adds new problem
     *
     * @param problemDto json object comes
     * @return Result
     */
    public Result add(ProblemDto problemDto) {

        Optional<Section> optionalSection = sectionRepository.findById(problemDto.getSectionId());
        if (!optionalSection.isPresent())
            return new Result("Such section not found", false);

        Section section = optionalSection.get();

        Problem problem = new Problem();
        problem.setTitle(problemDto.getTitle());
        problem.setBody(problemDto.getBody());
        problem.setSection(section);
        problemRepository.save(problem);
        return new Result("Problem added", true);
    }


    /**
     * this method returns all problems
     *
     * @return List<Problem>
     */
    public List<Problem> get() {
        return problemRepository.findAll();
    }


    /**
     * this method returns one problem by given id
     *
     * @param id Integer
     * @return Problem
     */
    public Problem getById(Integer id) {
        Optional<Problem> optionalProblem = problemRepository.findById(id);
        return optionalProblem.orElse(null);
    }


    /**
     * this methods deletes one problem by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = problemRepository.existsById(id);
        if (!exists)
            return new Result("Such problem not found", false);

        problemRepository.deleteById(id);
        return new Result("Problem deleted", true);
    }


    /**
     * this method edits one problem by given id
     *
     * @param id         Integer
     * @param problemDto json object comes
     * @return Result
     */
    public Result edit(Integer id, ProblemDto problemDto) {

        Optional<Problem> optionalProblem = problemRepository.findById(id);
        if (!optionalProblem.isPresent())
            return new Result("Such problem not found", false);

        Problem problem = optionalProblem.get();

        Optional<Section> optionalSection = sectionRepository.findById(problemDto.getSectionId());
        if (!optionalSection.isPresent())
            return new Result("Such section not found", false);


        problem.setSection(optionalSection.get());
        problem.setTitle(problemDto.getTitle());
        problem.setBody(problemDto.getBody());

        problemRepository.save(problem);
        return new Result("Problem edited", true);
    }
}
