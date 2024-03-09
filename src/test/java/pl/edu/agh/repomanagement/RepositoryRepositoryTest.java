import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RepositoryRepositoryTest {

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Test
    public void testSaveRepository() {
        Repository repository = new Repository("Example Repository", "http://example.com/repo");

        Repository savedRepository = repositoryRepository.save(repository);

        assertNotNull(savedRepository.getId());
    }
}
