package pl.edu.agh.repomanagement.backend.services;

import org.kohsuke.github.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.records.LastCommit;

import java.io.IOException;
import java.util.Date;
import java.util.List;


@Service
public class LastCommitServiceImpl implements LastCommitService {

    private static final Logger logger = LoggerFactory.getLogger(LastCommitServiceImpl.class);

    private GitHub github;

    public LastCommitServiceImpl() {
        try {
            this.github = GitHubBuilder.fromEnvironment()
                    .withRateLimitHandler(RateLimitHandler.FAIL)
                    .build();
        } catch (IOException e) {
            logger.error("Error occurred while creating GitHub instance", e);
        }
    }

    public LastCommit getLastCommit(Repository repository) {
        LastCommit lastCommit = null;

        try {

            String repoUrl = repository.getUrl();
            String[] splitUrl = repoUrl.split("/");
            String userName = splitUrl[3];
            String repoName = splitUrl[4];
            GHRepository gitRepository = github.getRepository(userName + "/" + repoName);
            System.out.println(gitRepository);
            List<GHCommit> listCommits = gitRepository.listCommits().toList();

            if (listCommits.isEmpty()) {
                return null;
            }

            GHCommit ghCommit = listCommits.get(0);

            String name = ghCommit.getAuthor().getName();
            Date date = ghCommit.getCommitDate();
            String message = ghCommit.getCommitShortInfo().getMessage();
            message = message.replace("\r", " ").replace("\n", " ");
            String url = ghCommit.getHtmlUrl().toString();

            lastCommit = new LastCommit(name, message, date, url);

        } catch (IOException e) {
            logger.error("Error occurred while fetching last commit", e);
        }
        return lastCommit;
    }
}
