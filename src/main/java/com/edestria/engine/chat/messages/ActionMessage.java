package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionMessage extends Message<ActionMessage> {

    public ActionMessage(String message) {
        super(message);
    }

    @Override
    public void sendAs(Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(this.getMessage()));
    }
}