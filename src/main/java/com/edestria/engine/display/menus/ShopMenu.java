package com.edestria.engine.display.menus;

import com.edestria.engine.chat.messages.ChatMessage;
import com.edestria.engine.chat.sounds.MessageSound;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.display.menus.rows.Rows;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import lombok.Getter;
import org.bukkit.Sound;

public class ShopMenu extends Menu {

    public static final MessageSound PURCHASE_SUCCESSFUL_SOUND = new MessageSound(Sound.ENTITY_EXPERIENCE_ORB_PICKUP);
    public static final MessageSound PURCHASE_UNSUCCESSFUL_SOUND = new MessageSound(Sound.BLOCK_ANVIL_PLACE);

    public static final String PURCHASE_SUCCESSFUL_MESSAGE = "$eYou purchased $6%s.";
    public static final String PURCHASE_UNSUCCESSFUL_MESSAGE = "$cYou do not have enough gold to purchase this!";

    @Getter
    public abstract class ShopItem extends MenuItem {

        private final int cost;

        public ShopItem(EItem eItem, int cost) {
            super(eItem);
            this.cost = cost;
        }

        @Override
        public void onClick(EPlayer ePlayer) {
            if (!purchasable(ePlayer, this, true)) {
                return;
            }
            this.purchase(ePlayer);
        }

        public abstract void purchase(EPlayer ePlayer);
    }

    public ShopMenu(String name, Rows rows) {
        super(name, rows);
    }


    public boolean purchasable(EPlayer ePlayer, ShopItem shopItem, boolean sound) {
        boolean purchasable = ePlayer.getGold() >= shopItem.getCost();
        ePlayer.playSound(sound ? purchasable ? ShopMenu.PURCHASE_SUCCESSFUL_SOUND : ShopMenu.PURCHASE_UNSUCCESSFUL_SOUND : null);
        if (sound) {
            ePlayer.sendMessage(new ChatMessage(purchasable ? String.format(ShopMenu.PURCHASE_SUCCESSFUL_MESSAGE, shopItem.getType().name()) : ShopMenu.PURCHASE_UNSUCCESSFUL_MESSAGE));
        }
        return purchasable;
    }

    public void addShopItem(Rows rows, int slot, ShopItem shopItem) {
        super.addMenuItem(rows, slot, shopItem);
    }
}
