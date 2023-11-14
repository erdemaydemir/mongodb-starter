package tr.org.povatr.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tr.org.povatr.mongodb.entity.User;
import tr.org.povatr.mongodb.repository.UserRepository;

@Service
public class UserService extends CommonReactiveMongoService<User> {

    public UserService(UserRepository userRepository) {
        super(userRepository);
    }
}
