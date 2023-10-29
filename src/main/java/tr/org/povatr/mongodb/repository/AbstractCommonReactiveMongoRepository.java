package tr.org.povatr.mongodb.repository;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;
import tr.org.povatr.mongodb.event.publisher.EntityEventPublisher;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractCommonReactiveMongoRepository<T extends BaseEntity<T>, ID extends Serializable> implements CommonReactiveMongoRepository<T, ID> {

    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final MongoEntityInformation<T, ID> entityInformation;
    private final EntityEventPublisher entityEventPublisher;
    private final Class<T> entityType;

    public AbstractCommonReactiveMongoRepository(ReactiveMongoTemplate reactiveMongoTemplate, EntityEventPublisher entityEventPublisher, MongoMappingContext mongoMappingContext, Class<T> entityType) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.entityEventPublisher = entityEventPublisher;
        this.entityInformation = new MappingMongoEntityInformation<T, ID>(Objects.requireNonNull((MongoPersistentEntity<T>) mongoMappingContext.getPersistentEntity(entityType)));
        this.entityType = entityInformation.getJavaType();
    }

    @Override
    public Mono<T> findById(ID id) {
        return reactiveMongoTemplate.findById(id, entityType);
    }

    @Override
    public Flux<T> findAll() {
        return reactiveMongoTemplate.findAll(entityType);
    }

    @Override
    public Mono<T> save(T entity) {
        return reactiveMongoTemplate.save(entity).doOnSuccess(t -> publishEvent(t, EntityEventType.SAVED));
    }

    @Override
    public Mono<T> update(ID id, T entity) {
        return reactiveMongoTemplate.findAndReplace(queryById(id), entity);
    }

    @Override
    public Mono<T> updateFields(ID id, Map<String, Object> fieldUpdates) {
        Query query = queryById(id);
        Update update = new Update();
        for (Map.Entry<String, Object> entry : fieldUpdates.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        return reactiveMongoTemplate.findAndModify(query, update, entityType);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return reactiveMongoTemplate.remove(findById(id)).then();
    }

    private void publishEvent(T t, EntityEventType type) {
        entityEventPublisher.publishEvent(t, type);
    }

    private Query queryById(ID id) {
        return new Query(Criteria.where(getIdProperty()).is(id));
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
