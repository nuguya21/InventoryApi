package com.github.nuguya21.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public record InventoryApiOpener(InventoryApi api) {

    public void open(@NotNull Player player) {
        Inventory inventory = api.getType() == null ?
                api.getTitle() == null ?
                        Bukkit.createInventory(api.getOwner(), api.getSize())
                        : Bukkit.createInventory(api.getOwner(), api.getSize(), api.getTitle()) : api.getTitle() == null ?
                Bukkit.createInventory(api.getOwner(), api.getType())
                : Bukkit.createInventory(api.getOwner(), api.getType(), api.getTitle());
        for (int i = 0; i < api.getSize(); i++) {
            if (api.getContents(player.getUniqueId())[i] != null) {
                inventory.setItem(i, api.getContents(player.getUniqueId())[i].getBase());
            }
        }
        player.openInventory(inventory);
    }
}
