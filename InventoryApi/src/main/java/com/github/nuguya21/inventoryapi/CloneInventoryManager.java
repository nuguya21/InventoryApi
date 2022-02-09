package com.github.nuguya21.inventoryapi;

import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CloneInventoryManager {
    private final Map<String, Set<Inventory>> clonesOfBase;
    private final Map<Inventory, String> baseOfClone;

    public CloneInventoryManager() {
        this.clonesOfBase = new HashMap<>();
        this.baseOfClone = new HashMap<>();
    }

    public Set<Inventory> getClones(InventoryApi base) {
        if (!clonesOfBase.containsKey(base.getName())) {
            Set<Inventory> inventorySet = new HashSet<>();
            this.clonesOfBase.put(base.getName(), inventorySet);
        }
        return this.clonesOfBase.get(base.getName());
    }

    public void addCloneInventory(InventoryApi base, Inventory inventory) {
        getClones(base).add(inventory);
        this.baseOfClone.put(inventory, base.getName());
    }

    public void removeCloneInventory(InventoryApi base, Inventory inventory) {
        getClones(base).remove(inventory);
        this.baseOfClone.remove(inventory);
    }

    public boolean isCloneInventory(InventoryApi base ,Inventory inventory) {
        return getClones(base).contains(inventory) && this.baseOfClone.containsKey(inventory);
    }

    public InventoryApi getInventoryApi(Inventory clone) {
        if (this.baseOfClone.containsKey(clone)) {
            return InventoryApiPlugin.getInventoryApi(this.baseOfClone.get(clone));
        }
        return null;
    }
}
