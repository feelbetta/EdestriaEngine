package com.edestria.engine.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

class ECommandInjector extends Command {

    protected ECommandInjector(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        return false;
    }
}
