package uz.pdp.task2.service;

import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.entity.ProblemTest;
import uz.pdp.task2.payload.ProblemTestDto;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.repository.ProblemRepository;
import uz.pdp.task2.repository.ProblemTestRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProblemTestService {

    final ProblemTestRepository problemTestRepository;
    final ProblemRepository problemRepository;

    public ProblemTestService(ProblemTestRepository problemTestRepository, ProblemRepository problemRepository) {
        this.problemTestRepository = problemTestRepository;
        this.problemRepository = problemRepository;
    }


    /**
     * this method returns all problemTest
     *
     * @return List<ProblemTest>
     */
    public List<ProblemTest> get() {
        return problemTestRepository.findAll();
    }


    /**
     * this method returns one problemTest by given id
     *
     * @param id Integer
     * @return ProblemTest
     */
    public ProblemTest getById(Integer id) {
        Optional<ProblemTest> optionalProblemTest = problemTestRepository.findById(id);
        return optionalProblemTest.orElse(null);
    }


    /**
     * this method deletes one problemTest by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = problemTestRepository.existsById(id);
        if (!exists)
            return new Result("Such problem test not found", false);

        problemTestRepository.deleteById(id);
        return new Result("Problem test deleted", true);
    }


    /**
     * this method adds new problemTest
     *
     * @param problemTestDto json object comes
     * @return Result
     */
    public Result add(ProblemTestDto problemTestDto) {

        ProblemTest problemTest = new ProblemTest();
        problemTest.setArguments(problemTestDto.getArguments());
        problemTest.setResult(problemTestDto.getResult());


        Optional<Problem> optionalProblem = problemRepository.findById(problemTestDto.getProblemId());
        if (!optionalProblem.isPresent())
            return new Result("Such problem not found", false);

        problemTest.setProblem(optionalProblem.get());

        problemTestRepository.save(problemTest);
        return new Result("Problem test added", true);

    }


    /**
     * this method edits one problemTest by given id
     *
     * @param id             Integer
     * @param problemTestDto json object comes
     * @return Result
     */
    public Result edit(Integer id, ProblemTestDto problemTestDto) {

        Optional<ProblemTest> optionalProblemTest = problemTestRepository.findById(id);
        if (!optionalProblemTest.isPresent())
            return new Result("Such problem test not found", false);

        ProblemTest problemTest = optionalProblemTest.get();
        problemTest.setArguments(problemTestDto.getArguments());
        problemTest.setResult(problemTestDto.getResult());

        Optional<Problem> optionalProblem = problemRepository.findById(problemTestDto.getProblemId());
        if (!optionalProblem.isPresent())
            return new Result("Such problem not found", false);

        problemTest.setProblem(optionalProblem.get());

        problemTestRepository.save(problemTest);
        return new Result("Problem test edited", true);
    }
}
