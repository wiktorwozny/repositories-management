package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.WorkspaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }

    @Override
    public Workspace saveWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Override
    public Workspace saveWorkspaceByName(String name) {
        Workspace workspace = new Workspace(name);
        return workspaceRepository.save(workspace);
    }

    @Override
    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    @Override
    public Workspace getWorkspaceById(String id) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(new ObjectId(id));
        return optionalWorkspace.orElse(null);
    }

    @Override
    public boolean deleteWorkspaceById(String id) {
        ObjectId objectId = new ObjectId(id);
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(objectId);
        if (optionalWorkspace.isPresent()) {
            workspaceRepository.delete(optionalWorkspace.get());
            return true;
        }
        return false;
    }

    @Override
    public Workspace updateWorkspace(String id, Workspace updatedWorkspace) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(new ObjectId(id));
        if (optionalWorkspace.isPresent()) {
            Workspace existingWorkspace = optionalWorkspace.get();
            existingWorkspace.setName(updatedWorkspace.getName());
            existingWorkspace.setRepositories(updatedWorkspace.getRepositories());
            return workspaceRepository.save(existingWorkspace);
        }
        return null;
    }

    @Override
    public Workspace addRepositoryToWorkspace(String id, Repository repository){
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(new ObjectId(id));
        if(optionalWorkspace.isPresent()){
            Workspace existingWorkspace = optionalWorkspace.get();
            existingWorkspace.addRepository(repository);
            return workspaceRepository.save(existingWorkspace);
        }
        return null;
    }


}
