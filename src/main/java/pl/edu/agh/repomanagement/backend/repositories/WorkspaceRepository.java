package pl.edu.agh.repomanagement.backend.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.repomanagement.backend.models.Workspace;

@Repository
public interface WorkspaceRepository extends MongoRepository<Workspace, ObjectId> {
}