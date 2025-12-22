package net.mokus.mokuslib.cooldown;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

public interface CooldownManager {

    void addCooldown(PlayerEntity player, Item item, int addedCooldown, int maxCooldown);
    int getCurrentCooldown(PlayerEntity player, Item item);
    boolean canUse(PlayerEntity player, Item item);
}
