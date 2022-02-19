package com.github.nuguya21.inventoryapi.utils;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class InventoryItem {
    private final ItemStack base;
    private final Consumer<InventoryClickEvent> clickEventConsumer;

    public InventoryItem(@NotNull ItemStack base, Consumer<InventoryClickEvent> consumer) {
        this.base = base;
        this.clickEventConsumer = consumer;
    }

    public void acceptClickEventConsumer(InventoryClickEvent event) {
        if (this.clickEventConsumer != null) {
            this.clickEventConsumer.accept(event);
        }
    }

    public ItemStack getBase() {
        return base;
    }
}
