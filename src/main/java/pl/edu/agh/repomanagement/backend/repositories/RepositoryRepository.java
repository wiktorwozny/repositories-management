package pl.edu.agh.repomanagement.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.repomanagement.backend.models.Repository;

public interface RepositoryRepository extends MongoRepository<Repository, String> {
}