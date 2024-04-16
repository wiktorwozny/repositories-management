package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;
import pl.edu.agh.repomanagement.backend.security.UserDetailsImpl;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        User encryptedUser = new User(user.getLogin(), passwordEncoder.encode(user.getPassword()));
        return userRepository.save(encryptedUser);
    }

    @Override
    public User getUserByLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        return optionalUser.orElse(null);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userRepository.findById(userDetails.getId()).orElse(null);
    }

    @Override
    public User updateUser(ObjectId userId, User updatedUser) {
        User userToBeUpdated = userRepository.findById(userId).orElse(null);
        if (userToBeUpdated == null) {
            return null;
        }

//        userToBeUpdated.setLogin(updatedUser.getLogin());
//        userToBeUpdated.setPassword(updatedUser.getPassword());
        userToBeUpdated.setWorkspaces(updatedUser.getWorkspaces());

        return userRepository.save(userToBeUpdated);

    }
}
