package com.github.nuguya21.inventoryapi.utils;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;

public class InventoryItem {
    private final ItemStack base;
    private Consumer<InventoryClickEvent> consumer;

    public InventoryItem(@NotNull ItemStack base, Consumer<InventoryClickEvent> consumer) {
        this.base = base;
        this.consumer = consumer;
    }

    public void setOnClickThis(Consumer<InventoryClickEvent> consumer) {
        this.consumer = consumer;
    }

    public void onClickThis(InventoryClickEvent event) {
        this.consumer.accept(event);
    }

    public ItemStack getBase() {
        return base;
    }
}
