package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;
import pl.edu.agh.repomanagement.backend.services.RepositoryService;
import pl.edu.agh.repomanagement.backend.services.WorkspaceService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/workspaces/{wid}/repositories")
@CrossOrigin(origins = "http://localhost:3000")
public class RepositoryController {

    private final RepositoryService repositoryService;
    private final WorkspaceService workspaceService;

    public static class CreateRepositoryDto {
        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public CreateRepositoryDto(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    @Autowired
    public RepositoryController(RepositoryService repositoryService, WorkspaceService workspaceService) {
        this.repositoryService = repositoryService;
        this.workspaceService = workspaceService;
    }

    @GetMapping
    public ResponseEntity<List<Repository>> getAllRepositories(@PathVariable("wid") String wid) {
        Workspace workspace = workspaceService.getWorkspaceById(wid);
        if(workspace == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Repository> repositories = workspace.getRepositories();
        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

    @GetMapping("/{rid}")
    public ResponseEntity<Repository> getRepository(@PathVariable("wid") String wid,
                                                    @PathVariable("rid") String rid) {

        Repository repository = repositoryService.getRepositoryById(rid);
        if(repository == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    @DeleteMapping("/{rid}")
    public ResponseEntity<Void> deleteRepository(@PathVariable("wid") String wid,
                                                 @PathVariable("rid") String rid) {
        Workspace workspace = workspaceService.getWorkspaceById(wid);
        if (workspace == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!repositoryService.deleteRepositoryById(rid)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Repository> repositories = workspace.getRepositories();
        if (repositories != null) {
            repositories.removeIf(repository -> repository.getId().equals(rid));
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<Repository> createRepository(@PathVariable("wid") String wid,
                                                       @RequestBody CreateRepositoryDto dto) {

        // get workspace
        Workspace workspace = workspaceService.getWorkspaceById(wid);
        if(workspace == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Repository repository = new Repository(dto.getName(), dto.getUrl());
        Repository newRepository = repositoryService.saveRepository(repository, wid);
        if(workspace.getRepositories() == null) {
            workspace.setRepositories(new ArrayList<>());
        }
        workspace.getRepositories().add(newRepository);

        return new ResponseEntity<>(newRepository, HttpStatus.CREATED);
    }

    @PutMapping("/{rid}")
    public ResponseEntity<Repository> updateRepository(@PathVariable("wid") String wid,
                                                       @PathVariable("rid") String rid,
                                                       @RequestBody Repository updatedRepository) {

        Repository repository = repositoryService.updateRepository(rid, updatedRepository);
        if( repository == null ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }
}
