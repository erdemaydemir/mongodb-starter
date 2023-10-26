package tr.org.povatr.mongodb.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import tr.org.povatr.mongodb.event.EntityEvent;

@Slf4j
@Component
public class EntityEventListener {

    @EventListener
    public void handleEntityEvent(EntityEvent event) {
        log.info("Event handle. class name -> {}, type -> {}", event.getEntity().getClass().getSimpleName(), event.getType());
    }
}
