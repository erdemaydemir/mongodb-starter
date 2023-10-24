package tr.org.povatr.mongodb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import tr.org.povatr.mongodb.Marker;
import tr.org.povatr.mongodb.repository.impl.CommonReactiveMongoRepositoryImpl;

@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@EnableMongoRepositories(repositoryBaseClass = CommonReactiveMongoRepositoryImpl.class)
public class MongoConfiguration {
}
