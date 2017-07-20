package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import com.edestria.engine.utils.lang.Lang;
import lombok.Getter;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ChatMessage extends Message<ChatMessage> {

    @Getter private final TextComponent textComponent;

    public ChatMessage(String textComponent) {
        super(textComponent);
        this.textComponent = new TextComponent(this.getMessage());
    }

    public ChatMessage withHoverMessage(String hoverMessage) {
        this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Lang.color(hoverMessage))}));
        return this;
    }

    public ChatMessage withURL(String url) {
        this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
        return this;
    }

    public ChatMessage withAdditionalChatMessage(ChatMessage additionalChatMessage) {
        this.textComponent.addExtra(additionalChatMessage.getTextComponent());
        return this;
    }

    @Override
    public void sendAs(Player player) {
        player.spigot().sendMessage(this.getTextComponent());
    }
}
