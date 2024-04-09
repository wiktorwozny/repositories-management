package pl.edu.agh.repomanagement.backend.services;

import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.records.LastCommit;

public interface LastCommitService {
    LastCommit getLastCommit(Repository repository);
}
