package tr.org.povatr.mongodb.event.listener;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;
import tr.org.povatr.mongodb.event.EntityEvent;

import java.io.Serializable;

@Slf4j
public class EntityEventListener<T extends BaseEntity<T>, ID extends Serializable> {

    private Sinks.Many<EntityEvent<T>> entityEvents;

    @PostConstruct
    public void init() {
        this.entityEvents = Sinks.many().replay().latest();
        this.listenToEntityEvents().subscribe();
    }

    public Flux<EntityEvent<T>> listenToEntityEvents() {
        return this.getEntityEvents()
                .doOnNext(tEntityEvent -> log.info(entityEvents.toString()));
    }

    public void emitEvent(T entity, EntityEventType type) {
        entityEvents.tryEmitNext(EntityEvent.<T>builder().type(type).entity(entity).id(entity.getId()).build());
    }

    public Flux<EntityEvent<T>> getEntityEvents() {
        return entityEvents.asFlux();
    }
}
