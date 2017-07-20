package com.edestria.engine.chat;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.utils.lang.Lang;
import com.edestria.engine.utils.time.Time;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

@Getter
public abstract class Message<T extends Message<T>> {

    private final String message;
    private MessageSound messageSound;
    private int duration;

    public Message(String message) {
        this.message = Lang.color(message);
    }

    public T withMessageSound(MessageSound messageSound) {
        this.messageSound = messageSound;
        return (T) this;
    }

    public T withDuration(TimeUnit timeUnit, int duration) {
        this.duration = (int) Time.toTicks(timeUnit, duration);
        return (T) this;
    }

    public void forPlayer(Player player) {
        if (this.messageSound != null) {
            messageSound.to(player);
        }
        if (this.duration < 1) {
            this.sendAs(player);
            return;
        }
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                Message.this.sendAs(player);
                if (i < Message.this.duration) {
                    i++;
                    return;
                }
                cancel();
                Message.this.finish();
                System.out.println("CALLED FINISH METHOD");
            }
        }.runTaskTimer(JavaPlugin.getPlugin(EdestriaEngine.class), 0, 1);
    }

    public abstract void sendAs(Player player);

    public void finish() {

    }
}
