package com.github.nuguya21.inventoryapi;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class InventoryApiManager {

    private final transient Set<String> names;
    private final Map<String, InventoryApi> inventoryApiMap = new HashMap<>();

    public InventoryApiManager() {
        this.names = new HashSet<>();
    }

    public void addInventoryApi(InventoryApi inventoryApi) {
        if (contain(inventoryApi.getName())) {
            this.names.add(inventoryApi.getName());
            this.inventoryApiMap.put(inventoryApi.getName(), inventoryApi);
        }
    }

    public void removeInventoryApi(String name) {
        if (name != null) {
            if (contain(name)) {
                this.names.remove(name);
                this.inventoryApiMap.remove(name);
            }
        }
    }

    public void removeInventoryApi(InventoryApi inventoryApi) {
        if (inventoryApi != null) {
            if (contain(inventoryApi)) {
                this.names.remove(inventoryApi.getName());
                this.inventoryApiMap.remove(inventoryApi.getName());
            }
        }
    }

    public InventoryApi getInventoryApi(String name) {
        if (name != null) {
            if (contain(name)) {
                return this.inventoryApiMap.get(name);
            }
        }
        return null;
    }

    public boolean contain(String name) {
        return this.names.contains(name);
    }
    public boolean contain(InventoryApi inventoryApi) {
        return this.names.contains(inventoryApi.getName());
    }

    public void open(@NotNull Player player, String name) {
        getInventoryApi(name).open(player);
    }
}
