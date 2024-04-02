package pl.edu.agh.repomanagement.backend.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.repomanagement.backend.models.Repository;

@RestController
public class CloneController {

    @PostMapping("/clone")
    public void cloneRepository(@RequestParam Repository repo, @RequestParam String destination) {
        try {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("git clone " + repo.getUrl() + " " + destination);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
