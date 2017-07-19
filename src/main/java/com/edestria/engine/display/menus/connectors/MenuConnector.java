package com.edestria.engine.display.menus.connectors;

import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.display.menus.Menu;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import lombok.Getter;
import org.bukkit.Sound;

@Getter
public class MenuConnector extends MenuItem {

    private MenuItem menuItem;

    private Menu from;
    private Menu to;

    public MenuConnector(Menu from, Menu to, EItem icon) {
        super(icon, new MessageSound(Sound.UI_BUTTON_CLICK));
        this.from = from;
        this.to = to;
    }

    @Override
    public void onClick(EPlayer ePlayer) {
        this.connect(ePlayer, ePlayer.getOpenMenu().equals(this.from) ? this.to : this.from);
    }

    public void connect(EPlayer ePlayer, Menu menu) {
        menu.openFor(ePlayer);
    }
}
