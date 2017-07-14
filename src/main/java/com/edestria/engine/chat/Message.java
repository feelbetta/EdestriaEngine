package com.edestria.engine.chat;

import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.Sendable;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public abstract class Message<T extends Message<T>> implements Sendable {

    private String message;
    private MessageSound messageSound;

    public Message(String message) {
        this.message = ChatColor.translateAlternateColorCodes('&', message);
    }

    public T withMessageSound(MessageSound messageSound) {
        this.messageSound = messageSound;
        return (T) this;
    }
}
