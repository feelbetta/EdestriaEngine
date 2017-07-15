package com.edestria.engine.database.mongo;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.Purgeable;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Map;

public abstract class DataService<Type, Identifier> implements Purgeable {

    @Getter private final Map<Identifier, Type> data;

    private final EdestriaEngine edestriaEngine;
    private final MongoCollection mongoCollection;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E MMMM d y hh:mm a z");

    public DataService(EdestriaEngine edestriaEngine, MongoCollection mongoCollection, Map<Identifier, Type> data) {
        this.edestriaEngine = edestriaEngine;
        this.mongoCollection = mongoCollection;
        this.data = data;
    }

    public Type retrieve(Identifier identifier, MongoDocumentIdentifier mongoDocumentIdentifier) {
        if (this.data.containsKey(identifier)) {
            return this.data.get(identifier);
        }
        Object type = new Object();
        if (this.exists(this.mongoCollection, mongoDocumentIdentifier)) {
            return (Type) type;
        }
        Document document = this.edestriaEngine.getMongoRetrievalService().get(this.mongoCollection, mongoDocumentIdentifier);
        type = this.edestriaEngine.getGsonService().deserialize(document.toJson(), type.getClass());
        return (Type) type;

    }

    public void update(MongoCollection mongoCollection, Object serialize, String identifier) {
        Document document = Document.parse(this.edestriaEngine.getGsonService().serialize(serialize));
        System.out.println(document);
        this.edestriaEngine.getMongoUpsertService()
                .append(mongoCollection,
                        new MongoDocumentIdentifier<>(identifier, document.getString(identifier)),
                        document)
                .push();
    }

    public void store(Identifier identifier, Type type) {
        this.data.put(identifier, type);
    }

    public  void unstore(Identifier identifier) {
        this.data.entrySet().removeIf(identifierTypeEntry -> identifierTypeEntry.getKey().equals(identifier));
    }

    public boolean exists(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier) {
        return this.edestriaEngine.getMongoRetrievalService().exists(mongoCollection, new MongoDocumentEntry<>(mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifier()));
    }
}
