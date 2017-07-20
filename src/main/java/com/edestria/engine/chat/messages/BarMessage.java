package com.edestria.engine.chat.messages;

import com.edestria.engine.chat.Message;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BarMessage extends Message<BarMessage> {

    private BossBar bossBar = Bukkit.createBossBar("", BarColor.PURPLE, BarStyle.SOLID, BarFlag.DARKEN_SKY);

    public BarMessage(String message) {
        super(message);
        this.bossBar.setTitle(message);
        this.bossBar.setProgress(0);
        this.bossBar.setVisible(true);
    }

    @Override
    public void sendAs(Player player) {
        if (!bossBar.getPlayers().contains(player)) {
            return;
        }
        this.bossBar.addPlayer(player);
    }

    public void remove() {
        this.bossBar.removeAll();
        this.bossBar = null;
    }
}
