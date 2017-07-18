package com.edestria.engine.display.menus;

import com.edestria.engine.chat.messages.TitleMessage;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.concurrent.TimeUnit;

public class ExampleMenu extends Menu {

    public ExampleMenu() {
        super("$eExample Menu!", Rows.THREE);
        this.fill(MenuDesign.BORDER, new MenuItem(
                new EItem(Material.LEATHER_HELMET)
                        .glowing()
                        .withArmorColor(Color.BLUE)
                        .withName("$Example Border")) {
            @Override
            public void onClick(EPlayer ePlayer) {
                ePlayer.sendMessage(new TitleMessage("welcome", "$eI LOVE YOU").withDuration(TimeUnit.SECONDS, 10).withMessageSound(new MessageSound(Sound.ENTITY_HORSE_ARMOR)));
            }
        });
    }
}
