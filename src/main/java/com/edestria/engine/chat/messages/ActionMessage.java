package com.edestria.engine.chat.messages;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.chat.Message;
import com.edestria.engine.utils.time.Time;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class ActionMessage extends Message<ActionMessage> {

    private int duration;

    public ActionMessage(String message) {
        super(message);
    }

    public ActionMessage withDuration(TimeUnit timeUnit, int duration) {
        this.duration = (int) Time.toTicks(timeUnit, duration);
        return this;
    }

    @Override
    public void to(Player player) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ActionMessage.this.getMessage()));
                i++;
                if (i < ActionMessage.this.duration) {
                    return;
                }
                cancel();
            }
        }.runTaskTimer(JavaPlugin.getPlugin(EdestriaEngine.class), 0, 1);
        if (this.getMessageSound() == null) {
            return;
        }
        getMessageSound().to(player);
    }
}
