package com.edestria.engine.chat.sounds;

import com.edestria.engine.sending.Sendable;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class MessageSound implements Sendable {

    private Sound sound;

    public MessageSound(Sound sound) {
        this.sound = sound;
    }

    @Override
    public void to(Player player) {
        player.playSound(player.getLocation(), this.sound, 1, 1);
    }
}
