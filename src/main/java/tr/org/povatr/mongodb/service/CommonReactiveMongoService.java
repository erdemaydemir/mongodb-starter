package tr.org.povatr.mongodb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tr.org.povatr.mongodb.repository.CommonReactiveMongoRepository;
import tr.org.povatr.mongodb.entity.BaseEntity;

import java.io.Serializable;
import java.util.Map;

@RequiredArgsConstructor
public abstract class CommonReactiveMongoService<T extends BaseEntity<T>, ID extends Serializable> {

    private final CommonReactiveMongoRepository<T, ID> commonReactiveMongoRepository;

    public Mono<T> findById(ID id) {
        return commonReactiveMongoRepository.findById(id);
    }

    public Flux<T> findAll() {
        return commonReactiveMongoRepository.findAll();
    }

    public Mono<T> save(T entity) {
        return commonReactiveMongoRepository.save(entity);
    }

    public Mono<T> update(ID id, T entity) {
        return commonReactiveMongoRepository.update(id, entity);
    }

    public Mono<T> update(ID id, Map<String, Object> fieldUpdates) {
        return commonReactiveMongoRepository.updateFields(id, fieldUpdates);
    }

    public Mono<Void> deleteById(ID id) {
        return commonReactiveMongoRepository.deleteById(id);
    }
}