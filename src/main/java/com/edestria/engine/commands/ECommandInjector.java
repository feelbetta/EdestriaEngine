package com.edestria.engine.commands;

import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.ranks.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class ECommandInjector extends Command {

    @Getter @Setter private Rank requiredRank;
    @Getter @Setter private EPlayer ePlayer;

    protected ECommandInjector(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
