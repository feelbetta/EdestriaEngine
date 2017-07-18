package com.edestria.engine.eplayers;

import com.edestria.engine.chat.Message;
import com.edestria.engine.ranks.Rank;
import com.edestria.engine.utils.lang.Lang;
import com.edestria.engine.utils.time.Time;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class EPlayer {

    private UUID uuid;
    private Rank rank;

    private String lastLogin;

    private String guild;

    private Set<UUID> friends;

    public EPlayer(UUID uuid) {
        this.uuid = uuid;
        this.rank = Rank.DEFAULT;
        this.lastLogin = Time.formatDate(new Date());
    }

    public EPlayer() {
    }

    public void sendMessage(Message message) {
        if (!isOnline()) {
            return;
        }
        message.to(getBukkitPlayer().get());
    }

    public void sendMessage(String message) {
        if (!isOnline()) {
            return;
        }
        getBukkitPlayer().get().sendMessage(Lang.color(message));
    }

    public Optional<Player> getBukkitPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.uuid));
    }


    public boolean isOnline() {
        return this.getBukkitPlayer().isPresent();
    }
}
