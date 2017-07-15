package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import com.edestria.engine.utils.time.Time;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class TitleMessage extends Message<TitleMessage> {

    private static final String SEPARATOR = "%n";
    private static final int IN_OUT_DELAY = 4;

    private int duration;

    public TitleMessage(String title, String subtitle) {
        super(title + TitleMessage.SEPARATOR + subtitle);
    }

    public TitleMessage(String title) {
        super(title);
    }

    public TitleMessage withDuration(TimeUnit timeUnit, int duration) {
        this.duration = (int) Time.toTicks(timeUnit, duration);
        return this;
    }

    @Override
    public void to(Player player) {
        boolean hasSubtitle = this.getMessage().contains(TitleMessage.SEPARATOR);
        String[] message = hasSubtitle ? this.getMessage().split(TitleMessage.SEPARATOR) : new String[]{this.getMessage()};
        player.sendTitle(message[0], message.length > 1 ? message[1] : "",  TitleMessage.IN_OUT_DELAY, this.duration, TitleMessage.IN_OUT_DELAY);

        if (this.getMessageSound() == null) {
            return;
        }
        getMessageSound().to(player);
    }
}
