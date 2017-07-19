package com.edestria.engine.display.menus;

import com.edestria.engine.display.menus.connectors.MenuConnector;
import com.edestria.engine.display.menus.rows.Rows;
import lombok.Getter;

@Getter
public class SubMenu extends Menu {

    private final Menu parent;

    public SubMenu(Menu parent, String name, Rows rows) {
        super(name, rows);
        this.parent = parent;
    }

    public void addMenuConnector(Rows rows, int slot, MenuConnector menuConnector) {
        this.addMenuItem(rows, slot, menuConnector);
        this.parent.addMenuItem(this.parent.getRows(), slot, menuConnector);
    }
}
