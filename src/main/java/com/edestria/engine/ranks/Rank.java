package com.edestria.engine.ranks;

import lombok.Getter;
import org.bukkit.ChatColor;

public enum Rank {

    DEFAULT("$7", false),
    VIP("$b[VIP]", 4.99, false),
    VIP_PLUS("$b[VIP+]", 8.99, false),
    MOD("$d[MOD]", true),
    OWNER("$4[OWNER]", true);

    private final String prefix;
    @Getter private final double price;
    @Getter private final boolean staff;

    Rank(String prefix, double price, boolean staff) {
        this.prefix = prefix;
        this.price = price;
        this.staff = staff;
    }

    Rank(String prefix, boolean staff) {
        this(prefix, 0.00, staff);
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('$', prefix);
    }

    @Override
    public String toString() {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('$', this.prefix));
    }
}
