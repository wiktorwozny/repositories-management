package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;

import java.util.List;

public interface WorkspaceService {
    Workspace saveWorkspace(Workspace workspace);
    Workspace saveUserWorkspace(Workspace workspace);

    Workspace saveWorkspaceByName(String name);
    Workspace saveUserWorkspaceByName(String name);

    List<Workspace> getAllWorkspaces();
    List<Workspace> getUserWorkspaces();

    Workspace getWorkspaceById(String id);

    Workspace deleteWorkspaceById(String id);
    Workspace deleteUserWorkspaceById(String id);

    Workspace updateWorkspace(String id, Workspace updatedWorkspace);

    Workspace addRepositoryToWorkspace(String workspaceId, Repository repository);
}
