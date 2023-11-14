package tr.org.povatr.mongodb.event.listener;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mapping.PersistentEntity;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterDeleteEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeDeleteEvent;
import org.springframework.stereotype.Component;
import tr.org.povatr.mongodb.annotation.DeleteModeDocument;
import tr.org.povatr.mongodb.entity.BaseEntity;
import tr.org.povatr.mongodb.enums.EntityEventType;
import tr.org.povatr.mongodb.event.EntityEvent;
import tr.org.povatr.mongodb.event.processor.AbstractEntityEventProcessor;
import tr.org.povatr.mongodb.event.processor.DefaultEntityEventProcessor;
import tr.org.povatr.mongodb.event.processor.MoveDeleteEventProcessor;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class EntityEventListener extends AbstractMongoEventListener<BaseEntity> {
    private final MongoMappingContext mongoMappingContext;
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    private Map<String, List<AbstractEntityEventProcessor<BaseEntity, String>>> collectionNameDeleteProcessorMap;
    private List<AbstractEntityEventProcessor<BaseEntity, String>> defaultProcessors;

    @PostConstruct
    public void init() {
        this.collectionNameDeleteProcessorMap = new HashMap<>();
        this.defaultProcessors = List.of(new DefaultEntityEventProcessor(reactiveMongoTemplate, null));
        settingUpDeleteProcessorForDeleteModeDocumentAnnotation(DeleteModeDocument.class);
    }

    @Override
    public void onBeforeDelete(BeforeDeleteEvent<BaseEntity> event) {
        List<AbstractEntityEventProcessor<BaseEntity, String>> processors = collectionNameDeleteProcessorMap.getOrDefault(event.getCollectionName(), defaultProcessors);
        processors.forEach(it -> it.accept(EntityEvent.builder()
                .entity(null)
                .query(event.getDocument())
                .type(EntityEventType.SAVED)
                .build()));
    }

    @Override
    public void onAfterDelete(AfterDeleteEvent<BaseEntity> event) {
        List<AbstractEntityEventProcessor<BaseEntity, String>> processors = collectionNameDeleteProcessorMap.getOrDefault(event.getCollectionName(), defaultProcessors);
        processors.forEach(it -> it.accept(EntityEvent.builder()
                .entity(null)
                .query(event.getDocument())
                .type(EntityEventType.SAVED)
                .build()));
    }

    private void settingUpDeleteProcessorForDeleteModeDocumentAnnotation(Class<? extends Annotation> annotation) {
        mongoMappingContext.getPersistentEntities()
                .stream()
                .filter(it -> it.isAnnotationPresent(annotation))
                .map(PersistentEntity::getType)
                .forEach(it -> addProcessorToMap(it.getAnnotation(Document.class)
                        .value(), new MoveDeleteEventProcessor(reactiveMongoTemplate, (MongoPersistentEntity<BaseEntity>) mongoMappingContext.getPersistentEntity(it))));
    }

    private void addProcessorToMap(String collectionName, AbstractEntityEventProcessor<BaseEntity, String> processor) {
        List<AbstractEntityEventProcessor<BaseEntity, String>> abstractEntityEventProcessors = collectionNameDeleteProcessorMap.get(collectionName);
        if (abstractEntityEventProcessors != null) {
            abstractEntityEventProcessors.add(processor);
        } else {
            collectionNameDeleteProcessorMap.put(collectionName, Collections.singletonList(processor));
        }
    }
}
