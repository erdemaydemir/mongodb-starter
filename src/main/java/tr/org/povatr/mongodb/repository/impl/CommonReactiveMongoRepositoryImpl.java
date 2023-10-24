package tr.org.povatr.mongodb.repository.impl;

import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleReactiveMongoRepository;
import reactor.core.publisher.Mono;

import java.io.Serializable;
import java.util.List;
import java.util.stream.StreamSupport;

public class CommonReactiveMongoRepositoryImpl<T, ID extends Serializable> extends SimpleReactiveMongoRepository<T, ID> {

    private final MongoEntityInformation<T, ID> entityInformation;
    private final ReactiveMongoOperations mongoOperations;

    public CommonReactiveMongoRepositoryImpl(MongoEntityInformation<T, ID> entityInformation, ReactiveMongoOperations mongoOperations, MongoEntityInformation<T, ID> entityInformation1, ReactiveMongoOperations mongoOperations1) {
        super(entityInformation, mongoOperations);
        this.entityInformation = entityInformation1;
        this.mongoOperations = mongoOperations1;
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return mongoOperations.findAndRemove(queryById(id), entityInformation.getJavaType()).then();
    }

    @Override
    public Mono<Void> delete(T entity) {
        return mongoOperations.findAndRemove(queryById(getId(entity)), entityInformation.getJavaType()).then();
    }

    @Override
    public Mono<Void> deleteAllById(Iterable<? extends ID> ids) {
        Query query = queryByIds(ids);
        return mongoOperations.findAllAndRemove(query, entityInformation.getJavaType()).then();
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> entities) {
        List<ID> ids = StreamSupport.stream(entities.spliterator(), false).map(this::getId).toList();
        return mongoOperations.findAllAndRemove(queryByIds(ids), entityInformation.getJavaType()).then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return mongoOperations.findAllAndRemove(queryForAll(), entityInformation.getJavaType()).then();
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
