package net.mokus.mokuslib.cooldown;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.mokus.mokuslib.api.MokusLibAPI;

public interface MokusLibCooldownItem {
    int getMaxCooldown(ItemStack item);
    int getAddedCooldown(ItemStack item);

    default boolean checkCooldown(PlayerEntity player, Item item) {
        return MokusLibAPI.get().canUse(player, item);
    }

    default void applyCooldown(PlayerEntity player, ItemStack item) {
        MokusLibAPI.get().addCooldown(player, item.getItem(), getAddedCooldown(item), getMaxCooldown(item));
    }
}
