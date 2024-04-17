package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.RateLimitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Comment;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.repositories.CommentRepository;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private final CommentRepository commentRepository;
    private final WorkspaceService workspaceService;
    private final CommentService commentService;

    private static final Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);
    private GitHub github;

    @Autowired
    public RepositoryServiceImpl(RepositoryRepository repositoryRepository, CommentRepository commentRepository, WorkspaceService workspaceService, CommentService commentService) {
        this.repositoryRepository = repositoryRepository;
        this.commentRepository = commentRepository;
        this.workspaceService = workspaceService;
        this.commentService = commentService;

        try {
            this.github = GitHubBuilder.fromEnvironment()
                    .withRateLimitHandler(RateLimitHandler.FAIL)
                    .build();
        } catch (IOException e) {
            logger.error("Error occurred while creating GitHub instance", e);
        }
    }

    @Override
    public Repository saveRepository(Repository repository, String workspaceId) {
        repository = repositoryRepository.save(repository);
        workspaceService.addRepositoryToWorkspace(workspaceId, repository);
        return repository;
    }

    @Override
    public Repository getRepositoryById(String id) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        return optRepository.orElse(null);
    }

    @Override
    public boolean deleteRepositoryById(String id) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        if (optRepository.isPresent()) {
            repositoryRepository.delete(optRepository.get());
            return true;
        }
        return false;
    }

    @Override
    public Repository updateRepository(String id, Repository updatedWorkspace) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        if (optRepository.isPresent()) {
            Repository repository = optRepository.get();
            repository.setName(updatedWorkspace.getName());
            repository.setUrl(updatedWorkspace.getUrl());
            return repositoryRepository.save(repository);
        }
        return null;
    }

    @Override
    public Comment addCommentToRepository(String id, String PRUrl, String commentText) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        if (optRepository.isPresent()) {
            Repository repository = optRepository.get();
            commentText.replace("\"", "");
            Comment comment = new Comment(PRUrl, commentText);
            commentRepository.save(comment);
            repository.addComment(comment);
            repositoryRepository.save(repository);
            try {
                String repoUrl = repository.getUrl();
                String[] splitUrl = repoUrl.split("/");
                String userName = splitUrl[3];
                String repoName = splitUrl[4];
                org.kohsuke.github.GHRepository ghRepository = github.getRepository(userName + "/" + repoName);
                List<GHPullRequest> ghPullRequests = ghRepository.queryPullRequests().list().toList();
                for (GHPullRequest ghPullRequest : ghPullRequests) {
                    System.out.println(ghPullRequest.getHtmlUrl().toString());
                    if (ghPullRequest.getHtmlUrl().toString().equals(comment.getPrUrl())) {
                        //without api key it doesn't work
                        ghPullRequest.comment(comment.getText());
                        return comment;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
