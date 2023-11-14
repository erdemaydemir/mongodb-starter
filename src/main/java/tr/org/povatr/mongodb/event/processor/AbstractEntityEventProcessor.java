package tr.org.povatr.mongodb.event.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.event.EntityEvent;

import java.io.Serializable;
import java.util.function.Consumer;

@RequiredArgsConstructor
public abstract class AbstractEntityEventProcessor<T extends BaseEntity, ID extends Serializable> implements Consumer<EntityEvent<T>> {

    protected final ReactiveMongoTemplate reactiveMongoTemplate;
    protected final MongoPersistentEntity<T> persistentEntity;

}
