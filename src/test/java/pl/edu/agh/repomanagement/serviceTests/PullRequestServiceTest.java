package pl.edu.agh.repomanagement.serviceTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.repomanagement.backend.records.PullRequest;
import pl.edu.agh.repomanagement.backend.services.PullRequestService;
import pl.edu.agh.repomanagement.backend.services.PullRequestServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PullRequestServiceImplTest {

    private PullRequestService pullRequestService;

    @BeforeEach
    public void setUp() {
        pullRequestService = new PullRequestServiceImpl();
    }

    @Test
    void testGetPullRequests() {
        // Given
        String repositoryUrl = "https://github.com/wiktorwozny/repositories-management";

        // When
        List<PullRequest> pullRequests = pullRequestService.getPullRequests(repositoryUrl);

        // Then
        for (PullRequest pullRequest : pullRequests) {
            assertTrue(pullRequest.url().startsWith("https://github.com/wiktorwozny/repositories-management/pull/"));
            assertFalse(pullRequest.title().isEmpty());
            assertFalse(pullRequest.userLogin().isEmpty());
        }
    }
}