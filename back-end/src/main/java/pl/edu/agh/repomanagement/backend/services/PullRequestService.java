package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.records.PullRequest;

import java.util.List;

public interface PullRequestService {
    List<PullRequest> getPullRequests(String repositoryUrl);
}