package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.services.CloneServiceImpl;

@RestController
public class CloneController {

    private final CloneServiceImpl cloneService;

    @Autowired
    public CloneController(CloneServiceImpl cloneService) {
        this.cloneService = cloneService;
    }

    @PostMapping("/clone")
    public String cloneRepository(@RequestParam Repository repo) {
        String command = cloneService.generateCloneCommand(repo);
        cloneService.copyToClipboard(command);
        return command;
    }
}
