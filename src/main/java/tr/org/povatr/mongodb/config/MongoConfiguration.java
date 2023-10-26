package tr.org.povatr.mongodb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import tr.org.povatr.mongodb.Marker;
import tr.org.povatr.mongodb.repository.impl.CommonReactiveMongoRepositoryImpl;

@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@EnableReactiveMongoRepositories(
        basePackages = "**.repository",
        repositoryBaseClass = CommonReactiveMongoRepositoryImpl.class)
@EnableReactiveMongoAuditing
public class MongoConfiguration {
}
