package pl.edu.agh.repomanagement.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.edu.agh.repomanagement.backend.controllers.RepositoryController;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;
import pl.edu.agh.repomanagement.backend.services.RepositoryService;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class RepositoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testgetAllRepositories() throws Exception
    {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        mockMvc.perform(get("/api/workspaces/{wid}/repositories", workspaceId.toHexString()))
                .andExpect(status().isOk());
    }

    @Test
    void testgetRepository() throws Exception
    {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Repository repository = new Repository("rtest", "utest");
        repositoryService.saveRepository(repository, workspaceId.toString());
        ObjectId repositoryId = repository.getId();

        mockMvc.perform(get("/api/workspaces/{wid}/repositories/{rid}",
                            workspaceId.toHexString(),
                            repositoryId.toHexString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testdeleteRepository() throws Exception
    {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Repository repository = new Repository("rtest", "utest");
        repositoryService.saveRepository(repository, workspaceId.toString());
        ObjectId repositoryId = repository.getId();

        mockMvc.perform(delete("/api/workspaces/{wid}/repositories/{rid}",
                        workspaceId.toHexString(),
                        repositoryId.toHexString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testcreateRepository() throws Exception
    {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        RepositoryController.CreateRepositoryDto dto = new RepositoryController.CreateRepositoryDto("rtest", "utest", "wtest");

        mockMvc.perform(post("/api/workspaces/{wid}/repositories",
                            workspaceId.toHexString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testupdateRepository() throws Exception
    {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();
        System.out.println(workspaceId.toString());

        Repository repository = new Repository("rtest", "utest");
        repository = repositoryService.saveRepository(repository, workspaceId.toString());
        ObjectId repositoryId = repository.getId();

        Repository newrepo = new Repository("new rtest", "new utest");

        mockMvc.perform(put("/api/workspaces/{wid}/repositories/{rid}",
                        workspaceId.toHexString(),
                        repositoryId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newrepo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
