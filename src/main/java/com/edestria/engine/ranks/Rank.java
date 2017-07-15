package com.edestria.engine.ranks;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Rank {

    DEFAULT(5, "$7", false),
    VIP(4, "$b[VIP]", 4.99, false),
    VIP_PLUS(3, "$b[VIP+]", 8.99, false),
    MOD(2, "$d[MOD]", true),
    OWNER(1, "$4[OWNER]", true);

    @Getter private final int priority;
    private final String prefix;
    @Getter private final double price;
    @Getter private final boolean staff;

    Rank(int priority, String prefix, double price, boolean staff) {
        this.priority = priority;
        this.prefix = prefix;
        this.price = price;
        this.staff = staff;
    }

    Rank(int priority, String prefix, boolean staff) {
        this(priority, prefix, 0.00, staff);
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('$', prefix);
    }

    public boolean isAtLeast(Rank rank) {
        return this.priority <= rank.getPriority();
    }

    @Override
    public String toString() {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('$', this.prefix));
    }
}
