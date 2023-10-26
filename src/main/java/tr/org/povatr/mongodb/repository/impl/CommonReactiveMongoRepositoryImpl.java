package tr.org.povatr.mongodb.repository.impl;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;
import tr.org.povatr.mongodb.event.EntityEvent;

import java.io.Serializable;
import java.util.List;
import java.util.stream.StreamSupport;

public class CommonReactiveMongoRepositoryImpl<T extends BaseEntity<T>, ID extends Serializable> extends SimpleReactiveMongoRepository<T, ID> {

    private final MongoEntityInformation<T, ID> entityInformation;
    private final ReactiveMongoOperations mongoOperations;

    public CommonReactiveMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, ReactiveMongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);
        this.entityInformation = entityInformation;
        this.mongoOperations = mongoOperations;
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return mongoOperations.findAndRemove(queryById(id), entityInformation.getJavaType()).doOnSuccess(ent -> register(ent, EntityEventType.DELETED)).then();
    }

    @Override
    public Mono<Void> delete(T entity) {
        return mongoOperations.findAndRemove(queryById(getId(entity)), entityInformation.getJavaType()).doOnSuccess(ent -> register(ent, EntityEventType.DELETED)).then();
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends ID> ids) {
        Query query = queryByIds(ids);
        return mongoOperations.findAllAndRemove(query, entityInformation.getJavaType()).doOnNext(ent -> register(ent, EntityEventType.DELETED)).then();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        List<ID> ids = StreamSupport.stream(entities.spliterator(), false).map(this::getId).toList();
        return mongoOperations.findAllAndRemove(queryByIds(ids), entityInformation.getJavaType()).doOnNext(ent -> register(ent, EntityEventType.DELETED)).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return mongoOperations.findAllAndRemove(queryForAll(), entityInformation.getJavaType()).doOnNext(ent -> register(ent, EntityEventType.DELETED)).then();
    }

    private Query queryById(ID id) {
        return new Query(Criteria.where(getIdProperty()).is(id));
    }

    private void register(T event, EntityEventType type) {
        event.registerEvent(EntityEvent.<T>builder().type(type).entity(event).build());
    }

    private Query queryForAll() {
        return new Query();
    }

    private Query queryByIds(Iterable<? extends ID> ids) {
        return new Query(Criteria.where(getIdProperty()).in(ids));
    }

    private ID getId(T entity) {
        return entityInformation.getId(entity);
    }

    private String getIdProperty() {
        return entityInformation.getIdAttribute();
    }
}
