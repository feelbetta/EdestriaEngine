package com.edestria.engine.commands;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.ranks.Rank;
import org.bukkit.entity.Player;

public class EFLSEF extends ECommand {

    public EFLSEF(EdestriaEngine edestriaEngine) {
        super(edestriaEngine);
    }

    @Override @CommandProperties(name = "lol", requiredRank = Rank.OWNER, usage = "Usage: /lol")
    public void perform(Player player, EPlayer ePlayer, String[] args) {
        player.sendMessage("woah gnarly brah");
    }
}
