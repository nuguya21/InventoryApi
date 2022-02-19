package com.github.nuguya21.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryApiPlugin extends JavaPlugin {

    private static InventoryApiManager inventoryApiManager;

    public static InventoryApiManager getInventoryApiManager() {
        return inventoryApiManager;
    }
    public static InventoryApi getInventoryApi(String name) {
        return getInventoryApiManager().getInventoryApi(name);
    }

    @Override
    public void onEnable() {
        instead = this;
        inventoryApiManager = new InventoryApiManager();
        Bukkit.getPluginManager().registerEvents(new InventoryApiListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static JavaPlugin getInstead() {
        return instead;
    }

    private static JavaPlugin instead;
}
