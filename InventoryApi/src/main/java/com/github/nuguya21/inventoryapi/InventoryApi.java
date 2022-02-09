package com.github.nuguya21.inventoryapi;

import com.github.nuguya21.inventoryapi.exception.NullSlotException;
import com.github.nuguya21.inventoryapi.utils.InventoryItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;


public class InventoryApi {

    private final String name;
    private final Inventory base;
    private InventoryType type;
    private String title;
    private final Map<Integer, InventoryItem> itemMap;
    private Consumer<InventoryClickEvent> onClick;
    private Consumer<InventoryCloseEvent> onClose;

    public InventoryApi(@NotNull String name, InventoryHolder owner, @NotNull InventoryType type, @Nullable String title) {
        this.name = name;
        if (title == null) this.base = Bukkit.createInventory(owner, type);
        else {
            this.base = Bukkit.createInventory(owner, type, title);
            this.title = title;
        }
        this.type = type;
        this.itemMap = new HashMap<>();
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, @NotNull InventoryType type, @Nullable Component title) {
        this.name = name;
        if (title == null) this.base = Bukkit.createInventory(owner, type);
        else {
            this.base = Bukkit.createInventory(owner, type, title);
            this.title = String.valueOf(title);
        }
        this.type = type;
        this.itemMap = new HashMap<>();
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, int size, @Nullable String title) {
        this.name = name;
        if (title == null) this.base = Bukkit.createInventory(owner, size);
        else {
            this.base = Bukkit.createInventory(owner, size, title);
            this.title = title;
        }
        this.itemMap = new HashMap<>();
    }

    public InventoryApi(@NotNull String name, InventoryHolder owner, int size, @Nullable Component title) {
        this.name = name;
        if (title == null) this.base = Bukkit.createInventory(owner, size);
        else {
            this.base = Bukkit.createInventory(owner, size, title);
            this.title = String.valueOf(title);
        }
        this.itemMap = new HashMap<>();
    }

    public InventoryItem getInventoryItem(int slot) {
        if (containOnSlot(slot)) {
            return this.itemMap.get(slot);
        }
        return null;
    }

    public void addInventoryItem(int slot, @NotNull InventoryItem item) throws NullSlotException {
        if (slot <= this.base.getSize() && slot >= 0) {
            if (containOnSlot(slot)) {
                this.itemMap.remove(slot);
            }
            this.itemMap.put(slot, item);
            this.base.setItem(slot, item.getBase());
        } else throw new NullSlotException();
    }

    public void removeInventoryItem(int slot) throws NullSlotException {
        if (slot <= this.base.getSize() && slot >= 0) {
            if (containOnSlot(slot)) {
                this.itemMap.remove(slot);
                this.base.clear(slot);
            }
        } else throw new NullSlotException();
    }

    public boolean containOnSlot(int slot) {
        return this.itemMap.containsKey(slot);
    }

    public Inventory getCloneInventory() {
        Inventory clone;
        if (type == null){
            if (title == null) clone = Bukkit.createInventory(this.base.getHolder(), this.base.getSize());
            else clone = Bukkit.createInventory(this.base.getHolder(), this.base.getSize(), title);
        } else {
            if (title == null) clone = Bukkit.createInventory(this.base.getHolder(), this.base.getType());
            else clone = Bukkit.createInventory(this.base.getHolder(), this.base.getType(), title);
        }
        if (!this.itemMap.isEmpty()) {
            ItemStack[] contents = this.base.getContents();
            if (contents.length > 0) clone.setContents(contents);
        }
        InventoryApiPlugin.getCloneInventoryManager().addCloneInventory(this, clone);
        return clone;
    }

    public void open(@NotNull Player player) {
        player.openInventory(this.base);
    }

    public void openClone(@NotNull Player player) {
        player.openInventory(getCloneInventory());
    }

    public void setOnClickAir(Consumer<InventoryClickEvent> consumer) {
        this.onClick = consumer;
    }

    public void setOnClose(Consumer<InventoryCloseEvent> consumer) {
        this.onClose = consumer;
    }

    public void onClickAir(InventoryClickEvent event) {
        this.onClick.accept(event);
    }

    public void onClose(InventoryCloseEvent event) {
        this.onClose.accept(event);
    }

    public String getName() {
        return this.name;
    }

    public void create() {
        InventoryApiPlugin.getInventoryApiManager().addInventoryApi(this);
    }
}
