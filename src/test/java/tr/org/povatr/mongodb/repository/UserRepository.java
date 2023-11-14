package tr.org.povatr.mongodb.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import tr.org.povatr.mongodb.entity.User;

@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
}
