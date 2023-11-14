package tr.org.povatr.mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import javax.persistence.Embedded;

@Getter
@Setter
public class BaseEntity {

    @Id
    private String id;

    @Embedded
    private Metadata metadata = new Metadata();

    @Version
    private Long version;
}
