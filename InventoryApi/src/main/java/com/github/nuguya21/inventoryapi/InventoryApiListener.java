package com.github.nuguya21.inventoryapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryApiListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();
        InventoryApi inventoryApi = InventoryApiPlugin.getCloneInventoryManager().getInventoryApi(inventory);
        if (inventoryApi != null) {
            InventoryApiPlugin.getCloneInventoryManager().removeCloneInventory(inventoryApi, inventory);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        InventoryApi inventoryApi = InventoryApiPlugin.getCloneInventoryManager().getInventoryApi(inventory);
        int slot = event.getSlot();
        if (inventoryApi != null) {
            if (event.getCurrentItem() == null || !event.getCurrentItem().hasItemMeta()) inventoryApi.onClickAir(event);
            else inventoryApi.getInventoryItem(slot).onClickThis(event);
        }
    }
}
