package pl.edu.agh.repomanagement.controllersTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.edu.agh.repomanagement.backend.controllers.CloneController;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.services.CloneServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CloneControllerTest {

    private CloneController cloneController;

    @Mock
    private CloneServiceImpl cloneService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        cloneController = new CloneController(cloneService);
    }

    @Test
    void cloneRepository_ReturnsCorrectCommand() {
        // Given
        Repository repository = new Repository("name", "https://example.com/repo.git");

        when(cloneService.generateCloneCommand(repository)).thenReturn("git clone " + repository.getUrl());

        // When
        String command = cloneController.cloneRepository(repository);

        // Then
        assertEquals("git clone https://example.com/repo.git", command);
    }

    @Test
    void cloneRepository_CopiesCommandToClipboard() {
        // Given
        Repository repository = new Repository("name", "https://example.com/repo.git");
        String expectedCommand = "git clone " + repository.getUrl();

        when(cloneService.generateCloneCommand(repository)).thenReturn(expectedCommand);

        // When
        cloneController.cloneRepository(repository);

        // Then
        verify(cloneService, times(1)).copyToClipboard(expectedCommand);
    }
}
