package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Comment;
import pl.edu.agh.repomanagement.backend.models.Repository;

public interface RepositoryService {
    Repository saveRepository(Repository repository, String workspaceId);

    Repository getRepositoryById(String id);

    boolean deleteRepositoryById(String id);

    Repository updateRepository(String id, Repository updatedRepository);

    Repository addCommentToRepository(String id,  String PRUrl, String commentText);
}
