package com.edestria.engine.commands.ecommands;

import com.edestria.engine.commands.ECommand;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CommandMaintenance extends ECommand {

    public CommandMaintenance(Plugin plugin) {
        super(plugin);
    }

    @Override @CommandProperties(name = "maintenance")
    public void perform(Player player, String[] args) {
        player.sendMessage("u sexy");
    }
}
