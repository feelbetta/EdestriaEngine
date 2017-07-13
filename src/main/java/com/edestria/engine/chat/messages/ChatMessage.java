package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import com.edestria.engine.chat.sounds.MessageSound;
import lombok.Builder;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ChatMessage extends Message {

    @Getter private TextComponent textComponent;
    private MessageSound messageSound;
    private String hover, url;

    private ChatMessage additional;

    @Builder
    public ChatMessage(String message, MessageSound messageSound, String hover, String url, ChatMessage additional) {
        super(message);
        this.textComponent = new TextComponent(this.getMessage());
        this.messageSound = messageSound;
        this.hover = hover;
        this.url = url;
        this.additional = additional;
    }

    @Override
    public void to(Player player) {
        if (this.hover != null) {
            this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(ChatColor.translateAlternateColorCodes('&', this.hover))}));
        }
        if (this.url != null) {
            this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.url));
        }
        if (this.additional != null) {
            this.textComponent.addExtra(this.additional.getTextComponent());
        }
        player.spigot().sendMessage(this.textComponent);
    }
}
