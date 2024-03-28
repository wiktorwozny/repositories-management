package pl.edu.agh.repomanagement.repositoryTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.WorkspaceRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
class WorkspaceRepositoryTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @AfterEach
    void tearDown() {
        workspaceRepository.deleteAll();
    }

    @Test
    void testSaveAndFindAll() {
        // Given
        Workspace workspace1 = new Workspace("Workspace 1");
        Workspace workspace2 = new Workspace("Workspace 2");

        // When
        workspaceRepository.save(workspace1);
        workspaceRepository.save(workspace2);

        // Then
        List<Workspace> workspaces = workspaceRepository.findAll();
        assertThat(workspaces).isNotNull();
        assertThat(workspaces).hasSize(2);
        assertThat(workspaces).contains(workspace1, workspace2);
    }

    @Test
    void testDeleteById() {
        // Given
        Workspace workspace = new Workspace("Workspace to delete");
        workspaceRepository.save(workspace);

        // When
        workspaceRepository.deleteById(workspace.getId());

        // Then
        assertThat(workspaceRepository.findById(workspace.getId())).isEmpty();
    }

    @Test
    void testUpdateWorkspace() {
        // Given
        Workspace workspace = new Workspace("Update Test Workspace");
        workspaceRepository.save(workspace);
        String newName = "Updated Workspace";

        // When
        workspace.setName(newName);
        workspaceRepository.save(workspace);

        // Then
        Optional<Workspace> updatedWorkspaceOptional = workspaceRepository.findById(workspace.getId());
        assertThat(updatedWorkspaceOptional).isPresent();
        Workspace updatedWorkspace = updatedWorkspaceOptional.get();
        assertThat(updatedWorkspace.getName()).isEqualTo(newName);
    }

    @Test
    void testFindById() {
        // Given
        Workspace workspace = new Workspace("Find Test Workspace");
        workspaceRepository.save(workspace);

        // When
        Optional<Workspace> foundWorkspaceOptional = workspaceRepository.findById(workspace.getId());

        // Then
        assertThat(foundWorkspaceOptional).isPresent();
        Workspace foundWorkspace = foundWorkspaceOptional.get();
        assertThat(foundWorkspace.getName()).isEqualTo(workspace.getName());
    }
}
