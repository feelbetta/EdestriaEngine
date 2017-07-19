package com.edestria.engine.eplayers;

import com.edestria.engine.chat.Message;
import com.edestria.engine.chat.MessageSequence;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.display.menus.Menu;
import com.edestria.engine.ranks.Rank;
import com.edestria.engine.utils.lang.Lang;
import com.edestria.engine.utils.time.Time;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

@Getter @Setter
public class EPlayer {

    private UUID uuid;
    private Rank rank;

    private String lastLogin;

    private int gold;
    private String guild;

    private Set<UUID> friends, friendRequests = new HashSet<>();

    private transient Menu openMenu;

    public EPlayer(UUID uuid) {
        this.uuid = uuid;
        this.rank = Rank.DEFAULT;
        this.guild = "none";
        this.lastLogin = Time.formatDate(new Date());
    }

    public EPlayer() {
    }

    public void playSound(MessageSound messageSound) {
        if (messageSound == null) {
            return;
        }
        this.getBukkitPlayer().ifPresent(messageSound::to);
    }

    public void sendMessage(MessageSequence messageSequence) {
        this.getBukkitPlayer().ifPresent(messageSequence::to);
    }

    public void sendMessage(Message message) {
        this.getBukkitPlayer().ifPresent(message::to);
    }

    public void sendMessage(String message) {
        this.getBukkitPlayer().ifPresent(player -> player.sendMessage(Lang.color(message)));
    }

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.uuid));
    }


    public boolean isOnline() {
        return this.getBukkitPlayer().isPresent();
    }
}
