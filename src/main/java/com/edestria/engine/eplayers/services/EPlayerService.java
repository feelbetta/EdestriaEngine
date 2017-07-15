package com.edestria.engine.eplayers.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.database.mongo.services.DataService;
import com.edestria.engine.eplayers.EPlayer;

import java.util.HashMap;
import java.util.UUID;

public class EPlayerService extends DataService<EPlayer, UUID> {

    public EPlayerService(EdestriaEngine edestriaEngine) {
        super(edestriaEngine, edestriaEngine.getMongoConnection().getActiveCollection("eplayers"), new HashMap<>(), EPlayer::new);
    }

/*
    @Override
    public EPlayer retrieve(UUID uuid) {
        if (this.eplayers.containsKey(uuid)) {
            return this.eplayers.get(uuid);
        }
        EPlayer ePlayer = new EPlayer(uuid);
        if (!this.exists(uuid)) {
            return ePlayer;
        }
        Document document = this.edestriaEngine.getMongoRetrievalService().get(EPlayerService.collection, new MongoDocumentIdentifier<>("uuid", uuid.toString()));
        ePlayer = this.edestriaEngine.getGsonService().deserialize(document.toJson(), EPlayer.class);
        return ePlayer;
    }

    @Override
    public void update(EPlayer ePlayer) {
        Document document = Document.parse(this.edestriaEngine.getGsonService().serialize(ePlayer));
        System.out.println(document);
        this.edestriaEngine.getMongoUpsertService()
                .append(EPlayerService.collection,
                        new MongoDocumentIdentifier<>("uuid", document.getString("uuid")),
                        document)
                .push();
    }

    @Override
    public void store(EPlayer ePlayer) {
        this.eplayers.put(ePlayer.getUuid(), ePlayer);
    }

    @Override
    public void unstore(EPlayer ePlayer) {
        this.eplayers.entrySet().removeIf(uuidePlayerEntry -> uuidePlayerEntry.getKey().equals(ePlayer.getUuid()));
    }

    @Override
    public boolean exists(UUID uuid) {
        return this.edestriaEngine.getMongoRetrievalService().exists(EPlayerService.collection, new MongoDocumentEntry<>("uuid", uuid.toString()));
    }
*/
}
