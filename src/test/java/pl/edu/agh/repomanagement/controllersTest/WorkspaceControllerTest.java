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
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class WorkspaceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    WorkspaceService workspaceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllWorkspaces() throws Exception {
        mockMvc.perform(get("/api/workspaces"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetWorkspaceById() throws Exception {
        Workspace workspace = new Workspace("Nazwa nowego workspace");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        mockMvc.perform(get("/api/workspaces/{id}", workspaceId.toHexString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDeleteWorkspace() throws Exception {
        Workspace workspace = new Workspace("Nazwa nowego workspace");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        mockMvc.perform(delete("/api/workspaces/{id}", workspaceId.toHexString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateWorkspace() throws Exception {
        String workspaceName = "New Workspace";

        ResultActions result = mockMvc.perform(post("/api/workspaces")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(workspaceName)));

        result.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateWorkspace() throws Exception {
        Workspace workspace = new Workspace("New Workspace");
        workspace = workspaceService.saveWorkspace(workspace);
        ObjectId workspaceId = workspace.getId();

        Workspace updatedWorkspace = new Workspace("Updated Workspace");

        updatedWorkspace = workspaceService.updateWorkspace(workspaceId.toHexString(), updatedWorkspace);

        ResultActions result = mockMvc.perform(put("/api/workspaces/{id}", workspaceId.toHexString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedWorkspace)));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
