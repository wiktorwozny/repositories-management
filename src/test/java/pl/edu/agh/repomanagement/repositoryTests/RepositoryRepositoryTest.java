package pl.edu.agh.repomanagement.repositoryTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class RepositoryRepositoryTest {

    @Autowired
    private RepositoryRepository repositoryRepository;

    @BeforeEach
    void tearDown() {
        repositoryRepository.deleteAll();
    }

    @Test
    void testSaveAndFindAll() {
        // Given
        Repository repository1 = new Repository("Repository 1", "url 1");
        Repository repository2 = new Repository("Repository 2", "url 2");

        // When
        repositoryRepository.save(repository1);
        repositoryRepository.save(repository2);

        // Then
        List<Repository> repositories = repositoryRepository.findAll();
        assertThat(repositories).isNotNull();
        assertThat(repositories).hasSize(2);
        assertThat(repositories).contains(repository1, repository2);
    }

    @Test
    void testDeleteById() {
        // Given
        Repository repository = new Repository("Repository to delete", "url");
        repositoryRepository.save(repository);

        // When
        repositoryRepository.deleteById(repository.getId());

        // Then
        assertThat(repositoryRepository.findById(repository.getId())).isEmpty();
    }

    @Test
    void testUpdateRepository() {
        // Given
        Repository repository = new Repository("Update Test Repository", "url");
        repositoryRepository.save(repository);
        String newName = "Updated Repository";

        // When
        repository.setName(newName);
        repositoryRepository.save(repository);

        // Then
        Optional<Repository> updatedRepositoryOptional = repositoryRepository.findById(repository.getId());
        assertThat(updatedRepositoryOptional).isPresent();
        Repository updatedRepository = updatedRepositoryOptional.get();
        assertThat(updatedRepository.getName()).isEqualTo(newName);
    }

    @Test
    void testFindById() {
        // Given
        Repository repository = new Repository("Find Test Repository", "url");
        repositoryRepository.save(repository);

        // When
        Optional<Repository> foundRepositoryOptional = repositoryRepository.findById(repository.getId());

        // Then
        assertThat(foundRepositoryOptional).isPresent();
        Repository foundRepository = foundRepositoryOptional.get();
        assertThat(foundRepository.getName()).isEqualTo(repository.getName());
    }
}