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
    private String hoverMessage, url;

    private ChatMessage additionalChatMessage;

    public ChatMessage(String textComponent) {
        super(textComponent);
        this.textComponent = new TextComponent(this.getMessage());
    }

    public ChatMessage withHoverMessage(String hoverMessage) {
        this.hoverMessage = hoverMessage;
        return this;
    }

    public ChatMessage withURL(String url) {
        this.url = url;
        return this;
    }

    public ChatMessage withAdditionalChatMessage(ChatMessage additionalChatMessage) {
        this.additionalChatMessage = additionalChatMessage;
        return this;
    }

    @Override
    public void to(Player player) {
        if (this.hoverMessage != null) {
            this.textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponent[]{new TextComponent(Lang.color(hoverMessage))}));
        }
        if (this.url != null) {
            this.textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, this.url));
        }
        if (this.additionalChatMessage != null) {
            this.textComponent.addExtra(this.additionalChatMessage.textComponent);
        }
        player.spigot().sendMessage(this.getTextComponent());
        if (this.getMessageSound() == null) {
            return;
        }
        getMessageSound().to(player);
    }
}
