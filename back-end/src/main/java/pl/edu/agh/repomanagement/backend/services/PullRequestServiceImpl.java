package pl.edu.agh.repomanagement.backend.services;

import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.records.PullRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PullRequestServiceImpl implements PullRequestService {

    private static final Logger logger = LoggerFactory.getLogger(PullRequestServiceImpl.class);

    private GitHub github;

    public PullRequestServiceImpl() {
        try {
            this.github = GitHubBuilder.fromEnvironment().build();
        } catch (IOException e) {
            logger.error("Error occurred while creating GitHub instance", e);
        }
    }

    public List<PullRequest> getPullRequests(String repositoryUrl) {
        List<PullRequest> pullRequests = new ArrayList<>();

        try {
            String[] repositoryParts = repositoryUrl.split("/");
            String owner = repositoryParts[3];
            String repoName = repositoryParts[4];
            org.kohsuke.github.GHRepository repository = github.getRepository(owner + "/" + repoName);

            List<GHPullRequest> ghPullRequests = repository.queryPullRequests().list().toList();

            for (GHPullRequest ghPullRequest : ghPullRequests) {
                PullRequest pullRequest = new PullRequest(
                        ghPullRequest.getHtmlUrl().toString(),
                        ghPullRequest.getTitle(),
                        ghPullRequest.getUser().getLogin()
                );
                pullRequests.add(pullRequest);
            }
        } catch (IOException e) {
            logger.error("Error occurred while fetching pull requests", e);
        }

        return pullRequests;
    }
}