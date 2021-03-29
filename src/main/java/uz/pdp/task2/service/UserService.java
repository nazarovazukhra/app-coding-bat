package uz.pdp.task2.service;

import org.springframework.stereotype.Service;
import uz.pdp.task2.entity.User;
import uz.pdp.task2.payload.Result;
import uz.pdp.task2.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * this method returns all users
     *
     * @return List<User>
     */
    public List<User> get() {
        return userRepository.findAll();
    }


    /**
     * this method returns one user by given id
     *
     * @param id Integer
     * @return User
     */
    public User getById(Integer id) {

        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }


    /**
     * this method adds new user
     *
     * @param user json object comes
     * @return Result
     */
    public Result add(User user) {

        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
        if (existsByEmail)
            return new Result("Such  email already exists", false);

        if (!(user.getPassword().length() >= 8)) {
            return new Result("Password length should be at least 8 characters", false);
        }

        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        userRepository.save(newUser);
        return new Result("User added", true);
    }


    /**
     * this method edits one user by given id
     *
     * @param id   Integer
     * @param user json object comes
     * @return Result
     */
    public Result edit(Integer id, User user) {

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent())
            return new Result("Such user not found", false);

        boolean exists = userRepository.existsByEmailAndIdNot(user.getEmail(), id);
        if (exists)
            return new Result("Such email already exists in another user", false);

        if (!(user.getPassword().length() >= 8))
            return new Result("Password length should be at least 8 characters", false);

        User editing = optionalUser.get();
        editing.setEmail(user.getEmail());
        editing.setPassword(user.getPassword());
        userRepository.save(editing);
        return new Result("User edited", true);
    }


    /**
     * this method deletes one user by given id
     *
     * @param id Integer
     * @return Result
     */
    public Result delete(Integer id) {

        boolean exists = userRepository.existsById(id);
        if (!exists)
            return new Result("Such user not found", false);

        userRepository.deleteById(id);
        return new Result("User deleted", true);
    }

}














