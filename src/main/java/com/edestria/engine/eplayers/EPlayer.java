package com.edestria.engine.eplayers;

import com.edestria.engine.chat.Message;
import com.edestria.engine.chat.MessageSequence;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.display.menus.Menu;
import com.edestria.engine.guilds.Guild;
import com.edestria.engine.invites.Invite;
import com.edestria.engine.invites.friend.FriendInvite;
import com.edestria.engine.invites.guild.GuildInvite;
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
    private String name;
    private Rank rank;

    private String firstLogin, lastLogin;

    private int gold;
    private String guild;
    private Set<Invite> invites = new HashSet<>();

    private Set<UUID> friends = new HashSet<>();

    private transient Menu openMenu;

    public EPlayer(UUID uuid) {
        this.uuid = uuid;
        this.rank = Rank.DEFAULT;
        this.lastLogin = Time.formatDate(new Date());
        this.lastLogin = Time.formatDate(new Date());
    }

    public void sendFriendRequest(EPlayer ePlayer) {
        ePlayer.getInvites().add(new FriendInvite(this));
    }

    public void acceptFriendRequest(EPlayer ePlayer) {
        this.denyFriendRequest(ePlayer);

        ePlayer.getFriends().add(ePlayer.getUuid());
        this.getFriends().add(ePlayer.getUuid());
    }

    public void denyFriendRequest(EPlayer ePlayer) {
        this.getInvites().removeIf(ePlayer.getUuid()::equals);
    }

    public void removeFriend(EPlayer ePlayer) {
        ePlayer.friends.removeIf(this.uuid::equals);
        this.friends.removeIf(ePlayer.getUuid()::equals);
    }
    
    public void sendGuildInvite(EPlayer ePlayer) {
        GuildInvite guildInvite = new GuildInvite(this);
        guildInvite.setType(this.guild);
        ePlayer.getInvites().add(guildInvite);
    }

    public void acceptGuildInvite(EPlayer ePlayer) {
        this.denyGuildInvite(ePlayer);
        this.setGuild(ePlayer.getGuild());
    }

    public void denyGuildInvite(EPlayer ePlayer) {
        this.getInvites().removeIf(ePlayer.getUuid()::equals);
    }

    public void leaveGuild(Guild guild) {
        this.guild = null;
        guild.getMembers().removeIf(this.uuid::equals);
    }
    public EPlayer() {
    }

    public boolean hasGuild() {
        return this.guild != null;
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
        this.getBukkitPlayer().ifPresent(message::forPlayer);
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
