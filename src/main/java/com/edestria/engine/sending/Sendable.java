package com.edestria.engine.sending;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface Sendable {

    void to(Player player);
}
