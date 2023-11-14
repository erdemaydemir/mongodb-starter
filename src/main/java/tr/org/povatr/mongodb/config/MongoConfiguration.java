package tr.org.povatr.mongodb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import tr.org.povatr.mongodb.Marker;

@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@EnableReactiveMongoAuditing
public class MongoConfiguration {
}
