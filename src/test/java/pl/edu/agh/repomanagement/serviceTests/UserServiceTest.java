package pl.edu.agh.repomanagement.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;
import pl.edu.agh.repomanagement.backend.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testSaveUser() {
        //Given
        User user = new User("Test User", "password");
        User encryptedUser = new User(user.getLogin(), passwordEncoder.encode(user.getPassword()));
        when(userRepository.save(any(User.class))).thenReturn(encryptedUser);

        //When
        User savedUser = userService.saveUser(user);

        //Then
        assertNotNull(savedUser);
        assertEquals(encryptedUser.getLogin(), savedUser.getLogin());
        assertEquals(encryptedUser.getPassword(), savedUser.getPassword());
    }

    @Test
    void testGetUserByLogin() {
        //Given
        User user = new User("Test User", "password");
        User ecryptedUser = new User(user.getLogin(), passwordEncoder.encode(user.getPassword()));
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(ecryptedUser));

        //When
        User foundUser = userService.getUserByLogin(user.getLogin());

        //Then
        assertNotNull(foundUser);
        assertEquals(ecryptedUser.getLogin(), foundUser.getLogin());
        assertEquals(ecryptedUser.getPassword(), foundUser.getPassword());
    }
}
