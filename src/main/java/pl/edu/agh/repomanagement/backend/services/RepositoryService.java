package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Repository;
import java.util.List;

public interface RepositoryService {
    Repository saveRepository(Repository repository);

    Repository getRepositoryById(String id);

    boolean deleteRepositoryById(String id);

    Repository updateRepository(String id, Repository updatedRepository);
}
