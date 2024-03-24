package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.orElse(null);
    }
}
