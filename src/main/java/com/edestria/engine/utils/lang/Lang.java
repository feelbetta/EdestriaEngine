package com.edestria.engine.utils.lang;

import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Lang {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('$', string);
    }

    public static String uncolor(String string) {
        return ChatColor.stripColor(string);
    }

    public static ItemStack[] updatePlaceholders(EPlayer ePlayer, Inventory inventory) {
        return Arrays.stream(inventory.getContents()).filter(itemStack -> itemStack.getItemMeta().hasLore() || itemStack.getItemMeta().hasDisplayName()).map(itemStack -> Lang.updatePlaceholders(ePlayer, itemStack)).toArray(ItemStack[]::new);
    }

    public static ItemStack updatePlaceholders(EPlayer ePlayer, ItemStack itemStack) {
        EItem eItem = new EItem(itemStack);
        ItemMeta itemMeta = eItem.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            eItem.withName(Lang.updatePlaceholders(ePlayer, itemMeta.getDisplayName()));
        }
        if (itemMeta.hasLore()) {
            eItem.withLore(Lang.updatePlaceholders(ePlayer, itemMeta.getLore()));
        }
        return eItem;
    }

    public static List<String> updatePlaceholders(EPlayer ePlayer, List<String> strings) {
        return strings.stream().map(s -> Lang.updatePlaceholders(ePlayer, s)).collect(Collectors.toList());
    }

    public static String updatePlaceholders(EPlayer ePlayer, String string) {
        return string.replace("%gold%", ePlayer.getGold() + "").replace("%name%", ePlayer.getName()).replace("%rank%", ePlayer.getRank().toString()).replace("%guild%", ePlayer.getGuild());
    }
}
