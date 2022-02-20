package com.github.nuguya21.inventoryapi;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InventoryApiManager {

    private final transient Set<String> names;
    private final Map<String, InventoryApi> inventoryApiMap = new HashMap<>();
    private final Map<UUID, InventoryApi> players = new HashMap<>();

    public InventoryApiManager() {
        this.names = new HashSet<>();
    }

    public void addInventoryApi(InventoryApi inventoryApi) {
        if (!contain(inventoryApi.getName())) {
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

    public void update(InventoryApi api) {
        if (api != null) {
            this.inventoryApiMap.put(api.getName(), api);
        }
    }

    public InventoryApi getOpeningInventoryApi(@NotNull Player player) {
        if (this.players.containsKey(player.getUniqueId())) {
            return this.players.get(player.getUniqueId());
        }
        return null;
    }

    @Deprecated
    public Collection<InventoryApi> getInventoryApis() {
        return this.inventoryApiMap.values();
    }

    public boolean contain(String name) {
        return this.names.contains(name);
    }
    public boolean contain(InventoryApi inventoryApi) {
        return this.names.contains(inventoryApi.getName());
    }

    public void open(String name, @NotNull Player player) {
        if (contain(name)) {
            getInventoryApi(name).open(player);
            this.players.put(player.getUniqueId(), getInventoryApi(name));
        }
    }

    public void close(@NotNull Player player) {
        this.players.remove(player.getUniqueId());
        player.closeInventory();
    }

    public void close(@NotNull Player player, boolean closeInventory) {
        this.players.remove(player.getUniqueId());
        if (closeInventory) player.closeInventory();
    }
}
