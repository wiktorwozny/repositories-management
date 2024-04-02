package pl.edu.agh.repomanagement.controllersTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.repomanagement.backend.controllers.CloneController;
import pl.edu.agh.repomanagement.backend.models.Repository;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloneControllerIntegrationTest {

    @Autowired
    private CloneController cloneController;

    @Test
    void testCloneRepository_Success() {
        String repoUrl = "https://github.com/geekcomputers/Python";
        String destination = "/tmp/test_repo";
        Repository repo = new Repository("test", repoUrl );
        cloneController.cloneRepository(repo, destination);
        assertThat(new File(destination)).exists();
        assertThat(new File(destination + "/.git")).exists();
    }
}
