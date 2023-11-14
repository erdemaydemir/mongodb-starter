package tr.org.povatr.mongodb.event.processor;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.event.EntityEvent;

import java.time.Instant;
import java.util.List;

import static tr.org.povatr.mongodb.constant.Constant.AGGREGATE_PUBLISHER;

@Slf4j
public class MoveDeleteEventProcessor extends AbstractEntityEventProcessor<BaseEntity, String> {

    public MoveDeleteEventProcessor(ReactiveMongoTemplate reactiveMongoTemplate, MongoPersistentEntity<BaseEntity> persistentEntity) {
        super(reactiveMongoTemplate, persistentEntity);
    }

    @Override
    public void accept(EntityEvent<BaseEntity> baseEntityEntityEvent) {
        Mono<Void> aggregateQuery = deleteAggregateQuery(baseEntityEntityEvent.getQuery());
        baseEntityEntityEvent.getQuery()
                .put(AGGREGATE_PUBLISHER, aggregateQuery);
        log.info("handle -> {}", baseEntityEntityEvent.getQuery()
                .toString());
    }

    private Mono<Void> deleteAggregateQuery(Document query) {
        MatchOperation match = Aggregation.match(AggregationExpression.from(MongoExpression.create(query.toJson())));
        List<AggregationOperation> pipeline = List.of(match, setDeletedAuditAggregationOperation(), mergeDeletedCollectionAggregationOperation());
        return reactiveMongoTemplate.aggregateAndReturn(persistentEntity.getType())
                .inCollection(persistentEntity.getCollection())
                .by(Aggregation.newAggregation(pipeline))
                .all()
                .then();
    }

    private MergeOperation mergeDeletedCollectionAggregationOperation() {
        return Aggregation.merge()
                .intoCollection(persistentEntity.getCollection()
                        .concat("_delete"))
                .build();
    }

    private SetOperation setDeletedAuditAggregationOperation() {
        return SetOperation.set("metadata.deletedDate")
                .toValue(Instant.now());
    }
}
