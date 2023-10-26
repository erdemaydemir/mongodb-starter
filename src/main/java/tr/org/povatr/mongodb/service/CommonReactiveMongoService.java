package tr.org.povatr.mongodb.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;

public interface CommonReactiveMongoService<T extends BaseEntity<T>> {

    Mono<T> findById(String id);

    Flux<T> findAll();

    Mono<T> save(T entity);

    Mono<T> update(String id, T entity);

    Mono<Void> delete(String id);
}