package tr.org.povatr.mongodb.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import tr.org.povatr.mongodb.Marker;
import tr.org.povatr.mongodb.config.properties.CommonMongoProperties;

@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@EnableReactiveMongoAuditing
@EnableConfigurationProperties({CommonMongoProperties.class})
public class MongoConfiguration {
}
