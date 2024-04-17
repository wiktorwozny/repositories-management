package pl.edu.agh.repomanagement.serviceTests;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.WorkspaceRepository;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkspaceServiceTest {

    @Autowired
    private WorkspaceService workspaceService;

    @MockBean
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void tearDown() {
        workspaceRepository.deleteAll();
    }

    @Test
    void testGetWorkspaceById() {
        // Given
        ObjectId id = new ObjectId();
        Workspace workspace = new Workspace("Test Workspace");
        when(workspaceRepository.findById(id)).thenReturn(Optional.of(workspace));

        // When
        Workspace retrievedWorkspace = workspaceService.getWorkspaceById(id.toHexString());

        // Then
        assertNotNull(retrievedWorkspace);
        assertEquals(workspace.getName(), retrievedWorkspace.getName());
    }

    @Test
    void testSaveWorkspace() {
        // Given
        Workspace workspace = new Workspace("Test Workspace");
        when(workspaceRepository.save(workspace)).thenReturn(workspace);

        // When
        Workspace savedWorkspace = workspaceService.saveWorkspace(workspace);

        // Then
        assertNotNull(savedWorkspace);
        assertEquals(workspace.getName(), savedWorkspace.getName());
    }

    @Test
    void testSaveWorkspaceByName() {
        // Given
        String name = "TestSaveByName";
        Workspace workspace = new Workspace(name);
        when(workspaceRepository.save(workspace)).thenReturn(workspace);

        // When
        Workspace savedWorkspace = workspaceService.saveWorkspaceByName(name);

        // Then
        assertNotNull(savedWorkspace);
        assertEquals(workspace.getName(), savedWorkspace.getName());
        verify(workspaceRepository, times(1)).save(workspace);
    }

    @Test
    void testGetAllWorkspaces() {
        // Given
        List<Workspace> workspaceList = new ArrayList<>();
        workspaceList.add(new Workspace("Workspace 1"));
        workspaceList.add(new Workspace("Workspace 2"));
        when(workspaceRepository.findAll()).thenReturn(workspaceList);

        // When
        List<Workspace> retrievedWorkspaces = workspaceService.getAllWorkspaces();

        // Then
        assertNotNull(retrievedWorkspaces);
        assertEquals(2, retrievedWorkspaces.size());
    }

    @Test
    void testDeleteWorkspaceById() {
        // Given
        ObjectId id = new ObjectId();
        Workspace workspace = new Workspace("Test Workspace");
        when(workspaceRepository.findById(id)).thenReturn(Optional.of(workspace));

        // When
        Workspace deletedWorkspace = workspaceService.deleteWorkspaceById(id.toHexString());

        // Then
        assertEquals(workspace, deletedWorkspace);
        verify(workspaceRepository, times(1)).delete(workspace);
    }

    @Test
    void testUpdateWorkspace() {
        // Given
        ObjectId workspaceId = new ObjectId();
        Workspace existingWorkspace = new Workspace("Existing Workspace");
        existingWorkspace.setId(workspaceId);

        Workspace updatedWorkspace = new Workspace("Updated Workspace");

        when(workspaceRepository.findById(workspaceId)).thenReturn(Optional.of(existingWorkspace));
        when(workspaceRepository.save(existingWorkspace)).thenReturn(existingWorkspace);

        // When
        Workspace result = workspaceService.updateWorkspace(workspaceId.toHexString(), updatedWorkspace);

        // Then
        assertNotNull(result);
        assertEquals(updatedWorkspace.getName(), result.getName());
        assertEquals(existingWorkspace.getId(), result.getId());
        verify(workspaceRepository, times(1)).findById(workspaceId);
        verify(workspaceRepository, times(1)).save(existingWorkspace);
    }

    @Test
    void testAddRepositoryToWorkspace() {
        // Given
        ObjectId workspaceId = new ObjectId();
        Workspace workspace = new Workspace();
        workspace.setId(workspaceId);
        List<Repository> repositories = new ArrayList<>();
        workspace.setRepositories(repositories);
        Repository repository = new Repository();
        repositories.add(repository);

        // When
        when(workspaceRepository.findById(workspaceId)).thenReturn(Optional.of(workspace));
        when(workspaceRepository.save(workspace)).thenReturn(workspace);

        Workspace updatedWorkspace = workspaceService.addRepositoryToWorkspace(workspaceId.toHexString(), repository);

        // Then
        assertNotNull(updatedWorkspace);
        assertEquals(repositories.size(), updatedWorkspace.getRepositories().size());
        verify(workspaceRepository, times(1)).findById(workspaceId);
        verify(workspaceRepository, times(1)).save(workspace);
    }

}
