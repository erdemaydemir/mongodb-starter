package tr.org.povatr.mongodb.event.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.event.EntityEvent;

@Slf4j
public class DefaultEntityEventProcessor extends AbstractEntityEventProcessor<BaseEntity, String> {

    public DefaultEntityEventProcessor(ReactiveMongoTemplate reactiveMongoTemplate, MongoPersistentEntity<BaseEntity> persistentEntity) {
        super(reactiveMongoTemplate, persistentEntity);
    }

    @Override
    public void accept(EntityEvent<BaseEntity> baseEntityEntityEvent) {
        log.info("handle -> {}", baseEntityEntityEvent.toString());
    }
}
