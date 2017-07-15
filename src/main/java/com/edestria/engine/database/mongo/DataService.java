package com.edestria.engine.database.mongo;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.Purgeable;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class DataService<Type, Identifier> implements Purgeable {

    @Getter private final Map<Identifier, Type> data;

    private final EdestriaEngine edestriaEngine;
    private final MongoCollection mongoCollection;

    private final Supplier<? extends Type> typeSupplier;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("E MMMM d y hh:mm a z");

    public DataService(EdestriaEngine edestriaEngine, MongoCollection mongoCollection, Map<Identifier, Type> data, Supplier<? extends Type> typeSupplier) {
        this.edestriaEngine = edestriaEngine;
        this.mongoCollection = mongoCollection;
        this.data = data;
        this.typeSupplier = Objects.requireNonNull(typeSupplier);
    }

    public Type retrieve(String key, Object value, Identifier identifier) {
        if (this.data.containsKey(identifier)) {
            return (Type) this.data.get(identifier);
        }
        MongoDocumentIdentifier mongoDocumentIdentifier = new MongoDocumentIdentifier<>(key, value);
        Type typeObject = typeSupplier.get();
        if (!this.exists(this.mongoCollection, mongoDocumentIdentifier)) {
            try {
                return (Type) typeSupplier.get().getClass().getConstructor(UUID.class).newInstance(identifier);
                /*
                *
                * Find way to grab constructor argument.
                * Otherwise, switch to full UUIDs ??
                * */
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
        Document document = this.edestriaEngine.getMongoRetrievalService().get(this.mongoCollection, mongoDocumentIdentifier);
        typeObject = this.edestriaEngine.getGsonService().deserialize(document.toJson(), typeSupplier.get().getClass());
        return (Type) typeObject;

    }

    public void update(String identifier, Object serialize) {
        Document document = Document.parse(this.edestriaEngine.getGsonService().serialize(serialize));
        System.out.println(document);
        this.edestriaEngine.getMongoUpsertService()
                .append(this.mongoCollection,
                        new MongoDocumentIdentifier<>(identifier, document.getString(identifier)),
                        document)
                .push();
    }

    public void store(Identifier identifier, Type type) {
        this.data.put(identifier, type);
    }

    public void unstore(Identifier identifier) {
        this.data.entrySet().removeIf(identifierTypeEntry -> identifierTypeEntry.getKey().equals(identifier));
    }

    public boolean exists(MongoCollection mongoCollection, MongoDocumentIdentifier mongoDocumentIdentifier) {
        return this.edestriaEngine.getMongoRetrievalService().exists(mongoCollection, new MongoDocumentEntry<>(mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifier()));
    }

    private String getValueType(Map map){
        String valueKind = Object.class.getName();
        Iterator<Map.Entry<Object,Object>> it = map.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Object,Object> entry = it.next();
            Object entryVal = entry.getValue();
            valueKind = entryVal.getClass().getName();
        }
        return valueKind;
    }
}
