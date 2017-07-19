package com.edestria.engine.display.menus.manager;

import com.edestria.engine.EdestriaEngine;
import com.edestria.engine.data.DataManager;
import com.edestria.engine.display.menus.Menu;
import com.edestria.engine.display.menus.items.MenuItem;
import com.edestria.engine.eplayers.EPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

public class MenuManager extends DataManager<Menu> implements Listener {

    public MenuManager(EdestriaEngine edestriaEngine) {
        super(edestriaEngine);
        Bukkit.getPluginManager().registerEvents(this, edestriaEngine);
    }

    public void register(Menu menu) {
        this.getData().putIfAbsent(menu.getName(), menu);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getInventory();
        if (event.getAction() == InventoryAction.NOTHING || !this.isMenu(inventory) || !this.getMenu(inventory).isPresent() || !this.getMenu(inventory).get().getMenuItem(event.getSlot()).isPresent()) {
            return;
        }
        event.setResult(Event.Result.DENY);
        Menu menu = this.getMenu(inventory).get();
        MenuItem menuItem = menu.getItemClickActions().get(event.getSlot());
        menuItem.onClick(this.getEdestriaEngine().getEPlayerService().retrieve("uuid", player.getUniqueId(), player.getUniqueId()));
        if (menu.isClosable(event.getSlot())) {
            player.closeInventory();
        }
        menuItem.getMessageSound().ifPresent(messageSound -> messageSound.to(player));
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!this.isMenu(event.getInventory())) {
            return;
        }
        EPlayer ePlayer = this.getEdestriaEngine().getEPlayerService().retrieve("uuid", event.getPlayer().getUniqueId(), event.getPlayer().getUniqueId());
        ePlayer.setOpenMenu(null);
    }

    public Optional<Menu> getMenu(Inventory inventory) {
        return Optional.ofNullable(this.getData().get(inventory.getTitle()));
    }

    private boolean isMenu(Inventory inventory) {
        return this.getData().values().stream().map(Menu::getInventory).map(Inventory::getTitle).anyMatch(inventory.getTitle()::equals);
    }
}
