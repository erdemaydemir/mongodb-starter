package tr.org.povatr.mongodb.entity;

import lombok.*;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.org.povatr.mongodb.event.EntityEvent;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Document
public class BaseEntity<T extends BaseEntity<T>> extends AbstractAggregateRoot<BaseEntity<T>> {

    @Id
    private String id;

    private Metadata metadata = new Metadata();

    public void registerEvent(EntityEvent<T> event) {
        super.registerEvent(event);
    }
}
