package com.github.nuguya21.inventoryapi;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

import static com.github.nuguya21.inventoryapi.InventoryApiPlugin.getInventoryApiManager;

public class InventoryApiListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player player) {
            getInventoryApiManager().close(player, false);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        int slot = event.getSlot();
        if (event.getWhoClicked() instanceof Player player) {
            InventoryApi api = getInventoryApiManager().getOpeningInventoryApi(player);
            if (api != null) {
                if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) {
                    api.acceptClickAirEventConsumer(event);
                } else {
                    api.getContents(player.getUniqueId())[slot].acceptClickEventConsumer(event);
                    player.sendMessage(api.getContents(player.getUniqueId())[slot].getBase().getType().name().toLowerCase());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryDrag(InventoryDragEvent event) {
        if (event.getWhoClicked() instanceof Player player) {
            InventoryApi api = getInventoryApiManager().getOpeningInventoryApi(player);
            if (api != null) {
                api.acceptDragEventConsumer(event);
            }
        }
    }
}
