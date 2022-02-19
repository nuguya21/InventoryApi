package com.github.nuguya21.inventoryapi;

import com.github.nuguya21.inventoryapi.utils.InventoryItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;


public class InventoryApi {

    private final String name;
    private InventoryHolder owner;
    private InventoryType type;
    private int size;
    private String title;
    private final Map<UUID, InventoryItem[]> contents = new HashMap<>();
    private Consumer<InventoryClickEvent> clickAirEventConsumer;
    private Consumer<InventoryDragEvent> dragEventConsumer;
    private Consumer<InventoryCloseEvent> closeEventConsumer;

    public InventoryApi(@NotNull String name, InventoryHolder owner, @NotNull InventoryType type) {
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.size = type.getDefaultSize();
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, @NotNull InventoryType type, @NotNull String title) {
        this.name = name;
        this.owner = owner;
        this.type = type;
        this.size = type.getDefaultSize();
        this.title = title;
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, int size) {
        this.name = name;
        this.owner = owner;
        this.type = null;
        this.size = size;
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, int size, @NotNull String title) {
        this.name = name;
        this.owner = owner;
        this.type = null;
        this.size = size;
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setOwner(InventoryHolder owner) {
        this.owner = owner;
    }
    public InventoryHolder getOwner() {
        return owner;
    }
    public void setType(InventoryType type) {
        this.type = type;
    }
    public InventoryType getType() {
        return type;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getSize() {
        return size;
    }

    @Deprecated
    public Map<UUID, InventoryItem[]> getContents() {
        return contents;
    }

    public InventoryItem[] getContents(@NotNull UUID uuid) {
        if (!this.contents.containsKey(uuid)) this.contents.put(uuid, new InventoryItem[this.size]);
        return this.contents.get(uuid);
    }

    public void setInventoryItem(@NotNull UUID uuid, int slot, InventoryItem item) {
        InventoryItem[] contents = getContents(uuid);
        contents[slot] = item;
        this.contents.put(uuid, contents);
    }

    public void removeInventoryItem(@NotNull UUID uuid, int slot) {
        InventoryItem[] contents = getContents(uuid);
        contents[slot] = null;
        this.contents.put(uuid, contents);
    }

    public void open(@NotNull Player player) {
        InventoryApiOpener opener = new InventoryApiOpener(this);
        opener.open(player);
    }

    public void setClickAirEventConsumer(Consumer<InventoryClickEvent> consumer) {
        this.clickAirEventConsumer = consumer;
    }
    public void setDragEventConsumer(Consumer<InventoryDragEvent> consumer) {
        this.dragEventConsumer = consumer;
    }
    public void setCloseEventConsumer(Consumer<InventoryCloseEvent> consumer) {
        this.closeEventConsumer = consumer;
    }

    public void acceptClickAirEventConsumer(InventoryClickEvent event) {
        if (this.clickAirEventConsumer != null) {
            this.clickAirEventConsumer.accept(event);
        }
    }
    public void acceptDragEventConsumer(InventoryDragEvent event) {
        if (this.dragEventConsumer != null) {
            this.dragEventConsumer.accept(event);
        }
    }
    public void acceptCloseEventConsumer(InventoryCloseEvent event) {
        if (this.closeEventConsumer != null) {
            this.closeEventConsumer.accept(event);
        }
    }

    public String getName() {
        return this.name;
    }

    public void create() {
        InventoryApiPlugin.getInventoryApiManager().addInventoryApi(this);
    }
}
