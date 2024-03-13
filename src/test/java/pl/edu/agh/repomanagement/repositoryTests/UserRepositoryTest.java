package pl.edu.agh.repomanagement.repositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testSaveAndFindAll() {

        // Given
        User user1 = new User("login1", "password1");
        User user2 = new User("login2", "password2");

        // When
        userRepository.save(user1);
        userRepository.save(user2);

        // Then
        List<User> users = userRepository.findAll();
        assertThat(users)
                .isNotNull()
                .hasSize(2)
                .contains(user1, user2);
    }

    @Test
    void testDeleteById() {
        // Given
        User user = new User("User to delete", "password");
        userRepository.save(user);

        // When
        userRepository.deleteById(user.getId());

        // Then
        assertThat(userRepository.findById(user.getId())).isEmpty();
    }

    @Test
    void testUpdateRepository() {
        // Given
        User user = new User("User to update", "password");
        userRepository.save(user);
        String login = "New login";

        // When
        user.setLogin(login);
        userRepository.save(user);

        // Then
        Optional<User> updatedUserOptional = userRepository.findById(user.getId());
        assertThat(updatedUserOptional).isPresent();
        User updateUser = updatedUserOptional.get();
        assertThat(updateUser.getLogin()).isEqualTo(login);
    }

    @Test
    void testFindById() {
        // Given
        User user = new User("Find Test User", "password");
        userRepository.save(user);

        // When
        Optional<User> userRepositoryOptional = userRepository.findById(user.getId());

        // Then
        assertThat(userRepositoryOptional).isPresent();
        User foundUser= userRepositoryOptional.get();
        assertThat(foundUser.getLogin()).isEqualTo(user.getLogin());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
    }
}