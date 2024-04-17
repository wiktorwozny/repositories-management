package pl.edu.agh.repomanagement.backend.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.repomanagement.backend.models.Comment;
import pl.edu.agh.repomanagement.backend.models.Repository;
import pl.edu.agh.repomanagement.backend.models.User;

import java.util.Optional;

public interface CommentRepository extends MongoRepository<Comment, ObjectId> {

    Optional<Comment> findByPrUrl(String prUrl);
}
