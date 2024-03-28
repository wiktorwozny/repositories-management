package pl.edu.agh.repomanagement.controllersTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.agh.repomanagement.backend.controllers.RepositoryController;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.services.RepositoryService;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
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
    void testGetAllRepositories() throws Exception {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        mockMvc.perform(get("/api/workspaces/{wid}/repositories", workspaceId.toHexString()))
                .andExpect(status().isOk());
    }

    @Test
    void testGetRepository() throws Exception {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Repository repository = new Repository("rtest", "utest");
        repositoryService.saveRepository(repository, workspaceId.toHexString());
        ObjectId repositoryId = repository.getId();

        mockMvc.perform(get("/api/workspaces/{wid}/repositories/{rid}",
                        workspaceId.toHexString(),
                        repositoryId.toHexString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteRepository() throws Exception {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Repository repository = new Repository("rtest", "utest");
        repositoryService.saveRepository(repository, workspaceId.toHexString());
        ObjectId repositoryId = repository.getId();

        mockMvc.perform(delete("/api/workspaces/{wid}/repositories/{rid}",
                        workspaceId.toHexString(),
                        repositoryId.toHexString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateRepository() throws Exception {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        RepositoryController.CreateRepositoryDto dto = new RepositoryController.CreateRepositoryDto("rtest", "utest");

        mockMvc.perform(post("/api/workspaces/{wid}/repositories",
                        workspaceId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateRepository() throws Exception {
        Workspace workspace = new Workspace("test");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Repository repository = new Repository("rtest", "utest");
        repository = repositoryService.saveRepository(repository, workspaceId.toHexString());
        ObjectId repositoryId = repository.getId();

        Repository newRepo = new Repository("new rtest", "new utest");

        mockMvc.perform(put("/api/workspaces/{wid}/repositories/{rid}",
                        workspaceId.toHexString(),
                        repositoryId.toHexString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRepo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
