package tr.org.povatr.mongodb.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.event.EntityEvent;

@Slf4j
@Component
public class EntityEventListener {

    @EventListener
    public <T extends BaseEntity<T>> void handleEntityEvent(EntityEvent<T> event) {
        log.info("Event handle. class name -> {}, type -> {}", event.getEntity().getClass().getSimpleName(), event.getType());
    }
}
