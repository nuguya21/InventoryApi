package com.github.nuguya21.inventoryapi;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryApiPlugin extends JavaPlugin {

    private static InventoryApiManager inventoryApiManager;
    private static CloneInventoryManager cloneInventoryManager;

    public static InventoryApiManager getInventoryApiManager() {
        return inventoryApiManager;
    }
    public static CloneInventoryManager getCloneInventoryManager() {
        return cloneInventoryManager;
    }

    public static InventoryApi getInventoryApi(String name) {
        return inventoryApiManager.getInventoryApi(name);
    }

    @Override
    public void onEnable() {
        instead = this;
        inventoryApiManager = new InventoryApiManager();
        cloneInventoryManager = new CloneInventoryManager();
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new InventoryApiListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getInstead() {
        return instead;
    }

    private static JavaPlugin instead;
}
