package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.User;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.repositories.UserRepository;
import pl.edu.agh.repomanagement.backend.repositories.WorkspaceRepository;
import pl.edu.agh.repomanagement.backend.security.UserDetailsServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final UserService userService;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository workspaceRepository, UserService userService) {
        this.workspaceRepository = workspaceRepository;
        this.userService = userService;
    }

    @Override
    public Workspace saveWorkspace(Workspace workspace) {
        return workspaceRepository.save(workspace);
    }

    @Override
    public Workspace saveUserWorkspace(Workspace workspace) {
        return assignWorkspaceToUser(saveWorkspace(workspace));
    }

    @Override
    public Workspace saveWorkspaceByName(String name) {
        Workspace workspace = new Workspace(name);
        return workspaceRepository.save(workspace);
    }

    @Override
    public Workspace saveUserWorkspaceByName(String name) {
        return assignWorkspaceToUser(saveWorkspaceByName(name));
    }

    @Override
    public List<Workspace> getAllWorkspaces() {
        return workspaceRepository.findAll();
    }

    public List<Workspace> getUserWorkspaces() {
        return userService.getAuthenticatedUser().getWorkspaces();
    }

    @Override
    public Workspace getWorkspaceById(String id) {
        System.out.println("ID: " + id);
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(new ObjectId(id));
        return optionalWorkspace.orElse(null);
    }

    @Override
    public Workspace deleteWorkspaceById(String id) {
        ObjectId objectId = new ObjectId(id);
        Workspace optionalWorkspace = workspaceRepository.findById(objectId).orElse(null);
        if (optionalWorkspace == null) {
            return null;
        }

        workspaceRepository.delete(optionalWorkspace);
        return optionalWorkspace;
    }

    @Override
    public Workspace deleteUserWorkspaceById(String id) {
        User user = userService.getAuthenticatedUser();
        boolean userHadWorkspace = user.removeWorkspace(getWorkspaceById(id));

        if (!userHadWorkspace) {
            return null;
        }
        userService.updateUser(user.getId(), user);

        Workspace deletedWorkspace = deleteWorkspaceById(id);
        return deletedWorkspace;
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
    public Workspace addRepositoryToWorkspace(String workspaceId, Repository repository) {
        Workspace workspace = getWorkspaceById(workspaceId);
        if (workspace != null) {
            List<Repository> repositories = workspace.getRepositories();
            if (repositories == null) {
                repositories = new ArrayList<>();
            }
            repositories.add(repository);
            workspace.setRepositories(repositories);
            return workspaceRepository.save(workspace);
        }
        return null;
    }

    private Workspace assignWorkspaceToUser(Workspace workspace) {
        User user = userService.getAuthenticatedUser();
        user.addWorkspace(workspace);
        userService.updateUser(user.getId(), user);
        return workspace;
    }

}
