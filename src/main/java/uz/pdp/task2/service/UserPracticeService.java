package uz.pdp.task2.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uz.pdp.task2.entity.Problem;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.entity.UserPractice;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.payload.UserPracticeDto;
import uz.pdp.task2.repository.ProblemRepository;
import uz.pdp.task2.repository.UserPracticeRepository;
import uz.pdp.task2.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserPracticeService {

    final UserPracticeRepository userPracticeRepository;
    final UserRepository userRepository;
    final ProblemRepository problemRepository;

    public UserPracticeService(UserPracticeRepository userPracticeRepository, UserRepository userRepository, ProblemRepository problemRepository) {
        this.userPracticeRepository = userPracticeRepository;
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
    }


    /**
     * this method returns all userPractice
     *
     * @return List<UserPractice>
     */
    public List<UserPractice> get() {
        return userPracticeRepository.findAll();
    }


    /**
     * this method returns one userPractice by given id
     *
     * @param id Integer
     * @return UserPractice
     */
    public UserPractice getById(Integer id) {
        Optional<UserPractice> optionalUserPractice = userPracticeRepository.findById(id);
        return optionalUserPractice.orElse(null);
    }


    /**
     * this method deletes one userPractice by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result deleted(Integer id) {

        boolean exists = userPracticeRepository.existsById(id);
        if (!exists)
            return new Result("Such user practice not found", false);

        userPracticeRepository.deleteById(id);
        return new Result("User practice deleted", true);
    }


    /**
     * this method adds new userPractice
     *
     * @param userPracticeDto json object comes
     * @return Result
     */
    public Result add(UserPracticeDto userPracticeDto) {

        UserPractice userPractice = new UserPractice();
        userPractice.setUserSolution(userPracticeDto.getUserSolution());
        userPractice.setScore(userPracticeDto.getScore());

        Optional<Problem> optionalProblem = problemRepository.findById(userPracticeDto.getProblemId());
        Optional<User> optionalUser = userRepository.findById(userPracticeDto.getUserId());

        if (!optionalProblem.isPresent())
            return new Result("Such problem not found", false);

        userPractice.setProblem(optionalProblem.get());

        if (!optionalUser.isPresent())
            return new Result("Such user not found", false);

        userPractice.setUser(optionalUser.get());

        userPracticeRepository.save(userPractice);
        return new Result("User practice added", true);


    }


    /**
     * this method edits one userPractice by given id
     *
     * @param id              Integer
     * @param userPracticeDto json object comes
     * @return Result
     */
    public Result edit(Integer id, UserPracticeDto userPracticeDto) {

        Optional<UserPractice> optionalUserPractice = userPracticeRepository.findById(id);
        if (!optionalUserPractice.isPresent())
            return new Result("Such user practice not found", false);

        UserPractice editingUserPractice = optionalUserPractice.get();
        editingUserPractice.setUserSolution(userPracticeDto.getUserSolution());
        editingUserPractice.setScore(userPracticeDto.getScore());


        Optional<Problem> optionalProblem = problemRepository.findById(userPracticeDto.getProblemId());
        Optional<User> optionalUser = userRepository.findById(userPracticeDto.getUserId());

        if (!optionalProblem.isPresent())
            return new Result("Such problem not found", false);

        editingUserPractice.setProblem(optionalProblem.get());

        if (!optionalUser.isPresent())
            return new Result("Such user not found", false);

        editingUserPractice.setUser(optionalUser.get());

        userPracticeRepository.save(editingUserPractice);
        return new Result("User practice added", true);

    }

}
