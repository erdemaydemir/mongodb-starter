package tr.org.povatr.mongodb.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Embeddable;
import java.time.Instant;

@Getter
@Setter
@Embeddable
public class Metadata {

    @CreatedDate
    private Instant createdDate;
    @CreatedBy
    private Object createdBy;
    @LastModifiedDate
    private Instant updatedDate;
    @LastModifiedBy
    private Object updatedBy;
    private Instant deletedDate;
    private Object deletedBy;
}
