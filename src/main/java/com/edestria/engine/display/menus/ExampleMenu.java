package com.edestria.engine.display.menus;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.display.menus.rows.Rows;
import com.edestria.engine.eplayers.EPlayer;

public class ExampleMenu extends Menu {

    public ExampleMenu(EdestriaEngine edestriaEngine) {
        super("Example", Rows.SIX);
        this.fill(new MenuItem(Menu.HOLDER, true) {
            @Override
            public void onClick(EPlayer ePlayer) {

            }
        });
        edestriaEngine.getMenuManager().register(this);
    }
}
