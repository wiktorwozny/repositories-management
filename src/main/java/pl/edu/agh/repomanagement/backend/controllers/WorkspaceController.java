package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000"})
@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @Autowired
    public WorkspaceController(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> getAllWorkspaces() {
        List<Workspace> workspaces = workspaceService.getAllWorkspaces();
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
        workspaceService.deleteWorkspaceById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Workspace> createWorkspace(@RequestBody String workspaceName) {
        Workspace workspace = new Workspace(workspaceName.replaceAll("\"", ""), new ArrayList<>());
        Workspace createdWorkspace = workspaceService.saveWorkspace(workspace);
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
