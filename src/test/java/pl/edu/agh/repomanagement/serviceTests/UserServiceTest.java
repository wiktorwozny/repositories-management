package pl.edu.agh.repomanagement.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;
import pl.edu.agh.repomanagement.backend.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    void testSaveUser() {
        //Given
        User user = new User("Test User", "password");
        when(userRepository.save(user)).thenReturn(user);

        //When
        User savedUser = userService.saveUser(user);

        //Then
        assertNotNull(userRepository.save(savedUser));
        assertEquals(user.getLogin(), savedUser.getLogin());
        assertEquals(user.getPassword(), savedUser.getPassword());
    }

    @Test
    void testGetUserByLogin() {
        //Given
        User user = new User("Test User", "password");
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));

        //When
        User foundUser = userService.getUserByLogin(user.getLogin());

        //Then
        assertNotNull(foundUser);
        assertEquals(user.getLogin(), foundUser.getLogin());
        assertEquals(user.getPassword(), foundUser.getPassword());
    }
}
