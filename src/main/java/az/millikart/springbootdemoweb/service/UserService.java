package az.millikart.springbootdemoweb.service;

import az.millikart.springbootdemoweb.entity.User;
import az.millikart.springbootdemoweb.exception.UserNotFoundException;
import az.millikart.springbootdemoweb.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    public User addUser(User user) {
        User addedUser = userRepository.save(user);
        log.info("New User : {}", addedUser);

        return addedUser;
    }


    public User updateUser(User user) {
        log.info("User : {}", user);
        this.getUserById(user.getId());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User userToDelete = this.getUserById(id);
        userRepository.delete(userToDelete);
    }
}
