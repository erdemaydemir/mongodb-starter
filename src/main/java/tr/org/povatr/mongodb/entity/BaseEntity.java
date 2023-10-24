package tr.org.povatr.mongodb.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
public class BaseEntity {

    @Id
    private String id;
    private Date createdDate;
    private String createdBy;
    private Date updatedDate;
    private String updatedBy;
}
