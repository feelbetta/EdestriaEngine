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
        this.bossBar.setTitle(this.getMessage());
        this.bossBar.setVisible(true);
        this.bossBar.setProgress(1);
    }

    @Override
    public void sendAs(Player player) {
        double progress = this.bossBar.getProgress() - (1 / (double) this.getDuration());
        this.bossBar.setProgress(this.isNegative(progress) ? 0 : progress);
        if (bossBar.getPlayers().contains(player)) {
            return;
        }
        this.bossBar.addPlayer(player);
    }

    @Override
    public void finish() {
        this.bossBar.removeAll();
        this.bossBar = null;
    }

    private boolean isNegative(double d) {
        return Double.doubleToRawLongBits(d) < 0;
    }
}
