package com.edestria.engine.chat;

import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.Sendable;
import com.edestria.engine.utils.lang.Lang;
import lombok.Getter;
import org.bukkit.ChatColor;

@Getter
public abstract class Message<T extends Message<T>> implements Sendable {

    private final String message;
    private MessageSound messageSound;

    public Message(String message) {
        this.message = Lang.color(message);
    }

    public T withMessageSound(MessageSound messageSound) {
        this.messageSound = messageSound;
        return (T) this;
    }
}
