package com.edestria.engine.display.menus.manager;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.data.DataManager;
import com.edestria.engine.display.menus.Menu;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class MenuManager extends DataManager<Menu> implements Listener {

    public MenuManager(EdestriaEngine edestriaEngine) {
        super(edestriaEngine);
    }

    public void register(Menu menu) {
        this.getData().put(menu.getName(), menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getAction() == InventoryAction.NOTHING || !this.isMenu(event.getInventory()) || !this.getMenu(event.getInventory()).isPresent()) {
            return;
        }
        Menu menu = this.getMenu(event.getInventory()).get();
        menu.getItemClickActions().get(event.getSlot()).onClick(this.getEdestriaEngine().getEPlayerService().retrieve("uuid", player.getUniqueId(), player.getUniqueId()));
        if (menu.isClosable(event.getSlot())) {
            player.closeInventory();
        }
    }

    public Optional<Menu> getMenu(Inventory inventory) {
        return Optional.of(this.getData().get(inventory.getTitle()));
    }

    private boolean isMenu(Inventory inventory) {
        return this.getData().values().stream().map(Menu::getInventory).map(Inventory::getTitle).anyMatch(inventory.getTitle()::equals);
    }
}
