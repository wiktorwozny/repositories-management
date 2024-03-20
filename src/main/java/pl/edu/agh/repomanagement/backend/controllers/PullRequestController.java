package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.repomanagement.backend.records.PullRequest;
import pl.edu.agh.repomanagement.backend.services.PullRequestServiceImpl;

import java.util.List;

@RestController
public class PullRequestController {

    private final PullRequestServiceImpl gitHubService;

    @Autowired
    public PullRequestController(PullRequestServiceImpl gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/pull-requests")
    public List<PullRequest> getPullRequests(@RequestParam String repositoryUrl) {
        return gitHubService.getPullRequests(repositoryUrl);
    }
}