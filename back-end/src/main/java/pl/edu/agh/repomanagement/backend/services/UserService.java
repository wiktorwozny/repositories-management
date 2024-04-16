package pl.edu.agh.repomanagement.backend.services;

import org.bson.types.ObjectId;
import pl.edu.agh.repomanagement.backend.models.User;

public interface UserService {

    User saveUser(User user);

    User getUserByLogin(String login);

    User getAuthenticatedUser();

    User updateUser(ObjectId userId, User updatedUser);

}
