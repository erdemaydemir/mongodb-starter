package tr.org.povatr.mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.entity.BaseEntity;

@RequiredArgsConstructor
public abstract class CommonReactiveMongoService<T extends BaseEntity> {

    protected final ReactiveMongoRepository<T, String> reactiveMongoRepository;

    public Mono<T> findById(String id) {
        return reactiveMongoRepository.findById(id);
    }

    public Flux<T> findAll() {
        return reactiveMongoRepository.findAll();
    }

    public Mono<T> save(T entity) {
        return reactiveMongoRepository.save(entity);
    }

    public Mono<T> update(String id, T entity) {
        entity.setId(id);
        return this.save(entity);
    }

    public Mono<Void> deleteById(String id) {
        return reactiveMongoRepository.deleteById(id);
    }
}