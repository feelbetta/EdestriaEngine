package com.edestria.engine.display.menus;

import com.edestria.engine.eplayers.EPlayer;
import com.edestria.engine.utils.items.EItem;
import com.edestria.engine.utils.lang.Lang;
import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public abstract class Menu {

    @AllArgsConstructor @Getter
    public enum Rows {

        ONE(9),
        TWO(18),
        THREE(27),
        FOUR(36),
        FIVE(45),
        SIX(54);

        private final int slots;
    }

    @Getter
    public abstract class MenuItem extends EItem {

        private boolean closable;

        public MenuItem(EItem eItem) {
            super(eItem);
        }

        public MenuItem(EItem eItem, boolean closable) {
            this(eItem);
            this.closable = closable;
        }

        public abstract void onClick(EPlayer ePlayer);
    }

    public enum MenuDesign {
        BORDER
    }

    private final String name;

    private final Rows rows;
    private final Inventory inventory;

    private final Map<Integer, MenuItem> itemClickActions = new HashMap<>();
    private final BiFunction<IntStream, MenuItem, Map<Integer, MenuItem>> slotMapping = (intStream, menuItem) -> intStream.boxed().collect(Collectors.toMap(slot -> slot, slot -> menuItem));

    public Menu(String name, Rows rows) {
        this.name = name;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(null, this.rows.getSlots(), Lang.color(name));
    }

    public void addItem(Rows rows, int slot, MenuItem menuItem) {
        Preconditions.checkArgument(slot > 9 || slot < 1, "Slot must be 1-9.");
        int position = (rows.getSlots() / 9) * 9 + slot - 9 - 1;
        this.inventory.setItem(position, menuItem);
        this.itemClickActions.put(position, menuItem);
    }

    public void fill(MenuItem menuItem) {
        this.slotMapping.apply(IntStream.range(0, this.inventory.getSize()), menuItem).forEach(this.inventory::setItem);
    }

    public void fill(MenuDesign menuDesign, MenuItem menuItem) {
        switch (menuDesign) {
            case BORDER:
                int size = this.inventory.getSize();
                int rows = size / 9;

                if (rows < 3) {
                    return;
                }
                this.slotMapping.apply(IntStream.range(0, 9), menuItem).forEach(this.inventory::setItem);
                IntStream.iterate(8, slot -> slot += 9).limit(this.inventory.getSize() - 9).forEach(slot -> {
                    int lastSlot = slot + 1;
                    this.inventory.setItem(slot, menuItem);
                    this.inventory.setItem(lastSlot, menuItem);
                });
                this.slotMapping.apply(IntStream.range(this.inventory.getSize() - 9, this.inventory.getSize()), menuItem).forEach(this.inventory::setItem);
                break;
        }
    }

    public Optional<MenuItem> getMenuItem(int slot) {
        return Optional.ofNullable(this.itemClickActions.get(slot));
    }

    public boolean isClosable(int slot) {
        Optional<MenuItem> menuItem = this.getMenuItem(slot);
        return menuItem.isPresent() && menuItem.get().isClosable();
    }

    public void openFor(Player player) {
        player.openInventory(this.inventory);
    }
}
