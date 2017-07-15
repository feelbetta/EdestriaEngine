package com.edestria.engine.chat;

import com.edestria.engine.Sendable;
import com.edestria.engine.utils.time.Time;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MessageSequence implements Sendable {

    private final Plugin plugin;

    private final List<Message> messages;
    private long delay;

    public MessageSequence(Plugin plugin, Message... messages) {
        this.messages = Arrays.asList(messages);
        this.plugin = plugin;
    }

    public MessageSequence withDelay(TimeUnit timeUnit, int delay) {
        this.delay = Time.toTicks(timeUnit, delay);
        return this;
    }

    @Override
    public void to(Player player) {
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                messages.get(i).to(player);
                i++;
                if (i < messages.size()) {
                    return;
                }
                cancel();
            }
        }.runTaskTimer(this.plugin, 0, this.delay);
    }
}
