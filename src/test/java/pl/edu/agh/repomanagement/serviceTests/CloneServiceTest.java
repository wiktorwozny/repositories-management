package pl.edu.agh.repomanagement.serviceTests;

import org.junit.jupiter.api.Test;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.services.CloneServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CloneServiceTest {

    @Test
    void generateCloneCommand_ReturnsCorrectCommand() {
        // Given
        CloneServiceImpl cloneService = new CloneServiceImpl();
        Repository repository = new Repository("name", "https://example.com/repo.git");
        String expectedCommand = "git clone https://example.com/repo.git";

        // When
        String command = cloneService.generateCloneCommand(repository);

        // Then
        assertEquals(expectedCommand, command);
    }
}
