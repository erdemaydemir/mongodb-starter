package tr.org.povatr.mongodb.repository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;

import java.io.Serializable;
import java.util.Map;

public interface CommonReactiveMongoRepository<T extends BaseEntity<T>, ID extends Serializable> {

    Mono<T> findById(ID id);

    Flux<T> findAll();

    Mono<T> save(T entity);

    Mono<T> update(ID id, T entity);

    Mono<T> updateFields(ID id, Map<String, Object> fieldUpdates);

    Mono<Void> deleteById(ID id);
}