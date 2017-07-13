package com.edestria.engine.chat;

import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.sending.Sendable;
import lombok.Getter;
import org.bukkit.ChatColor;

public abstract class Message implements Sendable {

    @Getter private String message;
    private MessageSound messageSound;

    public Message(String message) {
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }
}
