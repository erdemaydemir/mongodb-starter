package tr.org.povatr.mongodb.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.data.mongodb.common")
public class CommonMongoProperties {

    private Boolean softDelete;
    private Boolean deleteCollection;
    private String deleteCollectionPostFix = "_delete";
    private Boolean domainEvent;
}
