package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.repositories.RepositoryRepository;

import java.util.Optional;

import static java.util.Objects.nonNull;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private final RepositoryRepository repositoryRepository;
    private WorkspaceService workspaceService;


    @Autowired
    public RepositoryServiceImpl(RepositoryRepository repositoryRepository) {
        this.repositoryRepository = repositoryRepository;
    }

    @Override
    public Repository saveRepository(Repository repository, String workspaceId) {
        if (nonNull(workspaceService.addRepositoryToWorkspace(workspaceId, repository))){
            return repositoryRepository.save(repository);
        }
        return null;
    }

    @Override
    public Repository getRepositoryById(String id) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        return optRepository.orElse(null);
    }

    @Override
    public boolean deleteRepositoryById(String id) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        if(optRepository.isPresent()) {
            repositoryRepository.delete(optRepository.get());
            return true;
        }
        return false;
    }

    @Override
    public Repository updateRepository(String id, Repository updatedWorkspace) {
        Optional<Repository> optRepository = repositoryRepository.findById(new ObjectId(id));
        if(optRepository.isPresent()) {
            Repository repository = optRepository.get();
            repository.setName(updatedWorkspace.getName());
            repository.setUrl(updatedWorkspace.getUrl());
            return repositoryRepository.save(repository);
        }
        return null;
    }
}
