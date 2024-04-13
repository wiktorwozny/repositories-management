package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Repository;

public interface CloneService {
    String generateCloneCommand(Repository repo);

    void copyToClipboard(String text);
}
