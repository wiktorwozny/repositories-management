package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.services.LastCommitServiceImpl;
import pl.edu.agh.repomanagement.backend.records.LastCommit;

@RestController
public class LastCommitController {

    private final LastCommitServiceImpl gitHubService;
    @Autowired
    public LastCommitController(LastCommitServiceImpl gitHubService) {
        this.gitHubService = gitHubService;
    }

    public LastCommit getLastCommit (Repository repository) {
        return  gitHubService.getLastCommit(repository);
    }
}