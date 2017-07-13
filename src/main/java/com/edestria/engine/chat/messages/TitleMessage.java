package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.utils.time.Time;
import lombok.Builder;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class TitleMessage extends Message {

    private static final String SEPARATOR = "|_|";

    private long duration;

    @Builder
    public TitleMessage(String title, String subtitle, MessageSound messageSound, TimeUnit timeUnit, int duration) {
        super(title + TitleMessage.SEPARATOR + subtitle);
        this.duration = Time.toTicks(timeUnit, duration);
    }

    @Override
    public void to(Player player) {
        boolean hasSubtitle = this.getMessage().contains(TitleMessage.SEPARATOR);
        String[] message = hasSubtitle ? this.getMessage().split(TitleMessage.SEPARATOR) : new String[]{this.getMessage()};
        player.sendTitle(message[0], message.length > 1 ? message[1] : "",  20, (int) this.duration, 20);
    }
}
