package net.mokus.mokuslib.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.HashMap;
import java.util.Map;

public class CooldownComponent implements ServerTickingComponent, AutoSyncedComponent {
    private final PlayerEntity player;
    private final Map<Item, SemiCooldownData> cooldowns = new HashMap<>();

    public CooldownComponent(PlayerEntity player) {
        this.player = player;
    }

    public void addCooldown(Item item, int addedCooldown, int maxCooldown) {
        SemiCooldownData data = cooldowns.get(item);
        if (data != null) {
            data.currentCooldown = Math.min(data.currentCooldown + addedCooldown, maxCooldown);
            data.maxCooldown = maxCooldown;
            data.addedCooldown = addedCooldown;
        } else {
            cooldowns.put(item, new SemiCooldownData(addedCooldown, maxCooldown, addedCooldown));
        }
    }

    public int getCurrentCooldown(Item item) {
        SemiCooldownData data = cooldowns.get(item);
        return data != null ? data.currentCooldown : 0;
    }

    public int getMaxCooldown(Item item) {
        SemiCooldownData data = cooldowns.get(item);
        return data != null ? data.maxCooldown : 0;
    }

    public boolean canUse(Item item) {
        SemiCooldownData data = cooldowns.get(item);
        if (data == null) return true;
        return data.currentCooldown <= (data.maxCooldown + 10 - data.addedCooldown);
    }

    @Override
    public void serverTick() {
        cooldowns.entrySet().removeIf(entry -> {
            SemiCooldownData data = entry.getValue();
            data.currentCooldown = Math.max(data.currentCooldown - 1, 0);
            ModComponents.MOKUS_COOLDOWN.sync(this.player);
            return data.currentCooldown == 0;
        });
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        cooldowns.clear();

        if (nbt.contains("Cooldowns", 9)) {
            NbtList list = nbt.getList("Cooldowns", 10);

            for (int i = 0; i < list.size(); i++) {
                NbtCompound entry = list.getCompound(i);

                Identifier itemId = Identifier.tryParse(entry.getString("Item"));
                if (itemId != null && Registries.ITEM.containsId(itemId)) {
                    Item item = Registries.ITEM.get(itemId);
                    int current = entry.getInt("Current");
                    int max = entry.getInt("Max");
                    int added = entry.getInt("Added");

                    cooldowns.put(item, new SemiCooldownData(current, max, added));
                }
            }
        }
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup lookup) {
        NbtList list = new NbtList();

        for (Map.Entry<Item, SemiCooldownData> entry : cooldowns.entrySet()) {
            NbtCompound itemNbt = new NbtCompound();

            Identifier itemId = Registries.ITEM.getId(entry.getKey());
            SemiCooldownData data = entry.getValue();

            itemNbt.putString("Item", itemId.toString());
            itemNbt.putInt("Current", data.currentCooldown);
            itemNbt.putInt("Max", data.maxCooldown);
            itemNbt.putInt("Added", data.addedCooldown);

            list.add(itemNbt);
        }

        nbt.put("Cooldowns", list);
    }

    public static class SemiCooldownData {
        public int currentCooldown;
        public int maxCooldown;
        public int addedCooldown;

        public SemiCooldownData(int currentCooldown, int maxCooldown, int addedCooldown) {
            this.currentCooldown = currentCooldown;
            this.maxCooldown = maxCooldown;
            this.addedCooldown = addedCooldown;
        }
    }
}
