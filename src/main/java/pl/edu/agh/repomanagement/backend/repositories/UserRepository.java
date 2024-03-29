package pl.edu.agh.repomanagement.backend.repositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.repomanagement.backend.models.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    Optional<User> findByLogin(String login);

}