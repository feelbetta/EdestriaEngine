package com.edestria.engine.display.menus;

import com.edestria.engine.display.menus.design.MenuDesign;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.display.menus.rows.Rows;
import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import com.edestria.engine.utils.lang.Lang;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public abstract class Menu {

    private final String name;

    private final Rows rows;
    private final Inventory inventory;

    private final Map<Integer, MenuItem> itemClickActions = new HashMap<>();

    public static final EItem HOLDER = new EItem(Material.STAINED_GLASS_PANE).withData(7).withName(" ");

    public Menu(String name, Rows rows) {
        this.name = Lang.color(StringUtils.repeat(" ", ((30 - ("%n" + name).length()) / 2) + 5) + "$n" + name);
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, this.rows.getSlots(), this.name);
    }

    public void addMenuItem(Rows rows, int slot, MenuItem menuItem) {
        int position = rows.getRelativeSlot(rows, slot);
        this.inventory.setItem(position, menuItem);
        this.itemClickActions.put(position, menuItem);
    }

    public void fill(MenuItem menuItem) {
        IntStream.range(0, this.inventory.getSize()).forEach(slot -> {
            this.inventory.setItem(slot, menuItem);
            this.itemClickActions.put(slot, menuItem);
        });
    }

    public void fill(MenuDesign menuDesign, MenuItem menuItem) {
        switch (menuDesign) {
            case BORDER:
                break;
        }
    }

    public String getRawName() {
        return Lang.uncolor(this.name);
    }

    public Optional<MenuItem> getMenuItem(int slot) {
        return Optional.ofNullable(this.itemClickActions.get(slot));
    }

    public boolean isClosable(int slot) {
        Optional<MenuItem> menuItem = this.getMenuItem(slot);
        return menuItem.isPresent() && menuItem.get().isClosable();
    }

    public void openFor(EPlayer ePlayer) {
        Arrays.stream(this.inventory.getContents()).filter(Objects::nonNull).filter(itemStack -> itemStack.getItemMeta().hasLore() || itemStack.getItemMeta().hasDisplayName()).forEach(itemStack -> {
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (itemMeta.hasDisplayName()) {
                itemMeta.setDisplayName(this.updatePlaceholders(ePlayer, itemMeta.getDisplayName()));
            }
            if (itemMeta.hasLore()) {
                itemMeta.setLore(this.updatePlaceholders(ePlayer, itemMeta.getLore()));
            }
            itemStack.setItemMeta(itemMeta);
        });
        ePlayer.getBukkitPlayer().ifPresent(player -> player.openInventory(this.inventory));
        ePlayer.setOpenMenu(this);
    }

    public boolean isShop() {
        return this.itemClickActions.values().stream().anyMatch(ShopMenu.ShopItem.class::isInstance);
    }

    public ItemStack updatePlaceholders(EPlayer ePlayer, ItemStack itemStack) {
        EItem eItem = new EItem(itemStack);
        ItemMeta itemMeta = eItem.getItemMeta();
        if (itemMeta.hasDisplayName()) {
            eItem.withName(this.updatePlaceholders(ePlayer, itemMeta.getDisplayName()));
        }
        if (itemMeta.hasLore()) {
            eItem.withLore(this.updatePlaceholders(ePlayer, itemMeta.getLore()));
        }
        return eItem;
    }

    private List<String> updatePlaceholders(EPlayer ePlayer, List<String> strings) {
        return strings.stream().map(s -> s.replace("%gold%", ePlayer.getGold() + "").replace("%name%", ePlayer.getName()).replace("%rank%", ePlayer.getRank().toString()).replace("%guild%", ePlayer.getGuild())).collect(Collectors.toList());
    }

    private String updatePlaceholders(EPlayer ePlayer, String string) {
        return string.replace("%gold%", ePlayer.getGold() + "").replace("%name%", ePlayer.getName()).replace("%rank%", ePlayer.getRank().toString()).replace("%guild%", ePlayer.getGuild());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Menu)) {
            return false;
        }
        Menu menu = (Menu) obj;
        return menu.getName().equals(this.getName());
    }
}
