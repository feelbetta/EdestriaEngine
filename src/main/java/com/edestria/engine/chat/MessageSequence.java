package com.edestria.engine.chat;

import com.edestria.engine.Sendable;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;

public class MessageSequence implements Sendable {

    private final Plugin plugin;

    private final List<Message> messages;

    private int message;

    public MessageSequence(Plugin plugin, Message... messages) {
        this.messages = Arrays.asList(messages);
        this.plugin = plugin;
    }

    @Override
    public void to(Player player) {
        new BukkitRunnable() {
            @Override
            public void run() {
                MessageSequence.this.messages.get(MessageSequence.this.message).forPlayer(player);
                MessageSequence.this.message++;
                if (MessageSequence.this.message < MessageSequence.this.messages.size()) {
                    return;
                }
                cancel();
            }
        }.runTaskTimer(this.plugin, 0, MessageSequence.this.messages.get(this.message).getDuration());
    }
}
