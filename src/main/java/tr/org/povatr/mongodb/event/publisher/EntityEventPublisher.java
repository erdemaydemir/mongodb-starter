package tr.org.povatr.mongodb.event.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;
import tr.org.povatr.mongodb.event.EntityEvent;

@RequiredArgsConstructor
@Component
public class EntityEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public <T extends BaseEntity<T>> void publishEvent(T entity, EntityEventType type) {
        this.publishEvent(EntityEvent.<T>builder().entity(entity).type(type).build());
    }

    public <T extends BaseEntity<T>> void publishEvent(EntityEvent<T> event) {
        applicationEventPublisher.publishEvent(event);
    }

}
