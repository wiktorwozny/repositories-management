package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.services.LastCommitService;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;
    private final LastCommitService lastCommitService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService, LastCommitService lastCommitService ) {
        this.workspaceService = workspaceService;
        this.lastCommitService = lastCommitService;

    }

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getUserWorkspaces();
        for(var workspace : workspaces) {
            if( workspace.getRepositories() == null) {
                continue;
            }
            for(var repository : workspace.getRepositories()) {
                if(repository == null) {
                    continue;
                }
                repository.setLastCommit(lastCommitService.getLastCommit(repository));
            }
        }

        return new ResponseEntity<>(workspaces, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workspace> getWorkspaceById(@PathVariable("id") String id) {
        Workspace workspace = workspaceService.getWorkspaceById(id);
        if (workspace != null) {
            return new ResponseEntity<>(workspace, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkspace(@PathVariable("id") String id) {
        workspaceService.deleteUserWorkspaceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody String workspaceName) {
        Workspace workspace = new Workspace(workspaceName.replaceAll("\"", ""), new ArrayList<>());
        Workspace createdWorkspace = workspaceService.saveUserWorkspace(workspace);
        return new ResponseEntity<>(createdWorkspace, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workspace> updateWorkspace(@PathVariable("id") String id,
                                                     @RequestBody Workspace updatedWorkspace) {
        Workspace workspace = workspaceService.updateWorkspace(id, updatedWorkspace);
        if (workspace != null) {
            return new ResponseEntity<>(workspace, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
