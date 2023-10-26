package tr.org.povatr.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
