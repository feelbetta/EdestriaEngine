package com.edestria.engine.display.menus;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.display.menus.rows.Rows;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import org.bukkit.Material;

public class ExampleMenu extends Menu {

    public ExampleMenu(EdestriaEngine edestriaEngine) {
        super("Example", Rows.SIX);
        this.addMenuItem(Rows.ONE, 1, new MenuItem(new EItem(Material.DIAMOND).withName("$e%name%'s Statistics").withLore("$fYou have: $6%gold% Gold")) {
            @Override
            public void onClick(EPlayer ePlayer) {

            }
        });
        edestriaEngine.getMenuManager().register(this);
    }
}
