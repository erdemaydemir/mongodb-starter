package tr.org.povatr.mongodb.event;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;

@Getter
@Setter
@Builder
public class EntityEvent<T extends BaseEntity> {

    private T entity;
    private EntityEventType type;
    private Document query;
}
