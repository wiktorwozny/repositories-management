package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Workspace;

import java.util.List;

public interface WorkspaceService {
    Workspace saveWorkspace(Workspace workspace);

    Workspace saveWorkspaceByName(String name);

    List<Workspace> getAllWorkspaces();

    Workspace getWorkspaceById(String id);

    boolean deleteWorkspaceById(String id);

    Workspace updateWorkspace(String id, Workspace updatedWorkspace);
}
