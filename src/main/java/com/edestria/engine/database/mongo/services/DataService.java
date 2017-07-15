package com.edestria.engine.database.mongo.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.Purgeable;
import com.edestria.engine.database.mongo.MongoDocumentEntry;
import com.edestria.engine.database.mongo.MongoDocumentIdentifier;
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Supplier;

public class DataService<Type, Identifier> implements Purgeable {

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

    @SuppressWarnings("unchecked")
    public Type retrieve(String key, Object value, Identifier identifier) {
        if (this.data.containsKey(identifier)) {
            System.out.println("IS IN HASHMAP");
            return this.data.get(identifier);
        }
        MongoDocumentIdentifier mongoDocumentIdentifier = new MongoDocumentIdentifier<>(key, value);
        Type typeObject = typeSupplier.get();
        if (!this.exists(this.mongoCollection, mongoDocumentIdentifier)) {
            try {
                System.out.println("NOT IN DATABASE, CREATE A NEW INSTANCE OF CLASS.");
                return (Type) Arrays.stream(typeSupplier.get().getClass().getConstructors()).filter(constructor -> constructor.getParameters().length == 1).findFirst().orElse(null).newInstance(identifier);
                /*
                *
                * Find better way to grab constructor argument.
                * Otherwise, switch to full UUIDs ??
                * */
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
        System.out.println("FETCHING FROM DATABASE.");
        Document document = this.edestriaEngine.getMongoRetrievalService().get(this.mongoCollection, mongoDocumentIdentifier);
        typeObject = this.edestriaEngine.getGsonService().deserialize(document.toJson(), typeSupplier.get().getClass());
        return typeObject;

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
        System.out.println("IDENTIFIER: " + mongoDocumentIdentifier.getIdentifier());
        System.out.println("IDENTIFIER VALUE: " + mongoDocumentIdentifier.getIdentifierValue());
        return this.edestriaEngine.getMongoRetrievalService().exists(mongoCollection, new MongoDocumentEntry<>(mongoDocumentIdentifier.getIdentifier(), mongoDocumentIdentifier.getIdentifierValue()));
    }

    @Override
    public void purge() {
        this.data.clear();
    }

    /*
    *
    * Won't actually retrieve correct value during runtime, maybe useful for something in the future.
    *
    private String getValueType(Map<Object, Object> map){
        String valueKind = Object.class.getName();
        for (Map.Entry entry : map.entrySet()) {
            Object entryVal = entry.getValue();
            valueKind = entryVal.getClass().getName();
        }
        return valueKind;
    } */
}
