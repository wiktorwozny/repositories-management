package pl.edu.agh.repomanagement.controllersTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import pl.edu.agh.repomanagement.backend.controllers.PullRequestController;
import pl.edu.agh.repomanagement.backend.records.PullRequest;
import pl.edu.agh.repomanagement.backend.services.PullRequestServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PullRequestController.class)
@WithMockUser
class PullRequestControllerTest {

    @MockBean
    private PullRequestServiceImpl pullRequestService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPullRequests() throws Exception {
        // Given
        String repositoryUrl = "https://github.com/wiktorwozny/repositories-management";
        List<PullRequest> expectedPullRequests = new ArrayList<>();
        // When
        when(pullRequestService.getPullRequests(anyString())).thenReturn(expectedPullRequests);

        // Then
        mockMvc.perform(get("/pull-requests")
                        .param("repositoryUrl", repositoryUrl))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}