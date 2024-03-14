package pl.edu.agh.repomanagement.serviceTests;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;
import pl.edu.agh.repomanagement.backend.repositories.WorkspaceRepository;
import pl.edu.agh.repomanagement.backend.services.RepositoryService;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class RepositoryServiceTest {

    @Autowired
    private RepositoryService repositoryService;

    @MockBean
    private RepositoryRepository repositoryRepository;

    @MockBean
    private WorkspaceRepository workspaceRepository;

    @Test
    void testSaveRepository()
    {
        Workspace workspace = new Workspace("Repo name");
        when(workspaceRepository.save(workspace)).thenReturn(workspace);

        Workspace savedworkspace = workspaceRepository.save(workspace);

        ObjectId workspaceId = savedworkspace.getId();
        Repository repo = new Repository("Test name", "Test url");
        when(repositoryRepository.save(repo)).thenReturn(repo);

        Repository savedrepo = repositoryService.saveRepository(repo, workspaceId.toString());

        assertNotNull(savedrepo);
        assertEquals(savedrepo.getName(), repo.getName());
        assertEquals(savedrepo.getUrl(), repo.getUrl());
    }

    @Test
    void testGetRepositoryById()
    {
        Repository repo = new Repository("Name", "Url");
        repo.setId(new ObjectId());
        when(repositoryRepository.findById(repo.getId())).thenReturn(Optional.of(repo));

        Repository gotrepo = repositoryService.getRepositoryById(repo.getId().toHexString());

        assertNotNull(gotrepo);
        assertEquals(gotrepo.getName(), repo.getName());
        assertEquals(gotrepo.getUrl(), repo.getUrl());
    }

    @Test
    void testdeleteRepositoryById()
    {
        Repository repo = new Repository("Name", "Url");
        repo.setId(new ObjectId());
        when(repositoryRepository.findById(repo.getId())).thenReturn(Optional.of(repo));

        boolean res = repositoryService.deleteRepositoryById(repo.getId().toHexString());
        assertTrue(res);
    }

    @Test
    void testupdateRepository()
    {
        Repository repo = new Repository("Name", "Url");
        repo.setId(new ObjectId());
        Repository newrepo = new Repository("New Name", "New Url");
        when(repositoryRepository.findById(repo.getId())).thenReturn(Optional.of(repo));
        when(repositoryRepository.save(repo)).thenReturn(repo);

        Repository afterrepo = repositoryService.updateRepository(repo.getId().toHexString(), newrepo);

        assertNotNull(afterrepo);
        assertEquals(newrepo.getName(), afterrepo.getName());
        assertEquals(newrepo.getUrl(), afterrepo.getUrl());
    }
}
