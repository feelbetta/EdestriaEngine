package com.edestria.engine.guilds.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.data.DataTracker;
import com.edestria.engine.guilds.Guild;

import java.util.TreeMap;

public class GuildTracker extends DataTracker<Guild, String> {

    public GuildTracker(EdestriaEngine edestriaEngine) {
        super(edestriaEngine, "name", edestriaEngine.getMongoConnection().getActiveCollection("guilds"), new TreeMap<>(String.CASE_INSENSITIVE_ORDER), Guild::new);

    }

/*    @Override
    public Guild retrieve(String name) {
        if (this.getData().containsKey(name)) {
            return this.getData().get(name);
        }
        Guild guild = new Guild(name);
        if (!this.exists(name)) {
            return guild;
        }
        Document document = this.edestriaEngine.getMongoRetrievalService().get(GuildTracker.collection, new MongoDocumentIdentifier<>("name", name));
        guild = this.edestriaEngine.getGsonService().deserialize(document.toJson(), Guild.class);
        return guild;
    }

    @Override
    public void update(Guild guild) {
        Document document = Document.parse(this.edestriaEngine.getGsonService().serialize(guild));
        System.out.println(document);
        this.edestriaEngine.getMongoUpsertService()
                .append(GuildTracker.collection,
                        new MongoDocumentIdentifier<>("name", document.getString("name")),
                        document)
                .push();
    }

    @Override
    public void store(Guild guild) {
        this.getData().put(guild.getName(), guild);
    }

    @Override
    public void unstore(Guild guild) {
        this.getData().entrySet().removeIf(stringGuildEntry -> stringGuildEntry.getKey().equals(guild.getName()));
    }

    @Override
    public boolean exists(String name) {
        return this.edestriaEngine.getMongoRetrievalService().exists(GuildTracker.collection, new MongoDocumentEntry<>("name", name));
    }*/
}
