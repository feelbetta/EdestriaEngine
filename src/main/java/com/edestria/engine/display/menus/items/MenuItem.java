package com.edestria.engine.display.menus.items;

import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import lombok.Getter;

import java.util.Optional;

@Getter
public abstract class MenuItem extends EItem {

    private boolean closable;
    private MessageSound messageSound;

    public MenuItem(EItem eItem, boolean closable, MessageSound messageSound) {
        super(eItem);
        this.closable = closable;
        this.messageSound = messageSound;
    }

    public MenuItem(EItem eItem, boolean closable) {
        this(eItem, closable, null);
        this.closable = closable;
    }

    public MenuItem(EItem eItem, MessageSound messageSound) {
        this(eItem, false, messageSound);
    }

    public MenuItem(EItem eItem) {
        this(eItem, null);
    }

    public Optional<MessageSound> getMessageSound() {
        return Optional.ofNullable(this.messageSound);
    }

    public abstract void onClick(EPlayer ePlayer);
}
