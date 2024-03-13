package pl.edu.agh.repomanagement.backend.repositories;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.repomanagement.backend.models.User;
public interface UserRepository extends MongoRepository<User, ObjectId>{
}