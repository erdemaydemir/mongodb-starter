package tr.org.povatr.mongodb.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import tr.org.povatr.mongodb.annotation.DeleteModeDocument;
import tr.org.povatr.mongodb.enums.DeleteMode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("task-details")
@DeleteModeDocument(DeleteMode.SOFT)
public class User extends BaseEntity {
    private String name;
    private String lastname;
}
