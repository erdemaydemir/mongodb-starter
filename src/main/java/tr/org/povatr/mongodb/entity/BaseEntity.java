package tr.org.povatr.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class BaseEntity<T extends BaseEntity<T>> {

    @Id
    private String id;

    private final Metadata metadata = new Metadata();
}
