package tr.org.povatr.mongodb.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntityEvent<T extends BaseEntity<T>> {

    private T entity;
    private EntityEventType type;
}
