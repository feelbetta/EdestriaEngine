package com.edestria.engine.eplayers.services;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.data.DataTracker;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.time.Time;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class EPlayerTracker extends DataTracker<EPlayer, UUID> implements Listener {

    public EPlayerTracker(EdestriaEngine edestriaEngine) {
        super(edestriaEngine, "uuid", edestriaEngine.getMongoConnection().getActiveCollection("eplayers"), new HashMap<>(), EPlayer::new);
        Bukkit.getServer().getPluginManager().registerEvents(this, edestriaEngine);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        EPlayer ePlayer = this.retrieve("uuid", event.getPlayer().getUniqueId().toString(), event.getPlayer().getUniqueId());
        ePlayer.setLastLogin(Time.formatDate(new Date()));
        ePlayer.setName(event.getPlayer().getName());
        this.store(event.getPlayer().getUniqueId(), ePlayer);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        EPlayer ePlayer = this.retrieve("uuid", event.getPlayer().getUniqueId().toString(), event.getPlayer().getUniqueId());
        this.update("uuid", ePlayer);
        this.unstore(event.getPlayer().getUniqueId());
        System.out.println("Stored size: " + this.getData().size());
    }

}