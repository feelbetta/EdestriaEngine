package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import org.bukkit.entity.Player;

public class TitleMessage extends Message<TitleMessage> {

    private static final String SEPARATOR = "%n";
    private static final int IN_OUT_DELAY = 4;

    public TitleMessage(String title, String subtitle) {
        super(title + TitleMessage.SEPARATOR + subtitle);
    }

    public TitleMessage(String title) {
        super(title);
    }

    @Override
    public void forPlayer(Player player) {
        boolean hasSubtitle = this.getMessage().contains(TitleMessage.SEPARATOR);
        String[] message = hasSubtitle ? this.getMessage().split(TitleMessage.SEPARATOR) : new String[]{this.getMessage()};
        if (this.getMessageSound() != null) {
            this.getMessageSound().to(player);
        }
        if (this.getDuration() < 1) {
            player.sendTitle(message[0], message.length > 1 ? message[1] : "",  TitleMessage.IN_OUT_DELAY, 30, TitleMessage.IN_OUT_DELAY);
            return;
        }
        player.sendTitle(message[0], message.length > 1 ? message[1] : "",  TitleMessage.IN_OUT_DELAY, this.getDuration(), TitleMessage.IN_OUT_DELAY);
    }

    @Override
    public void sendAs(Player player) {
    }
}
