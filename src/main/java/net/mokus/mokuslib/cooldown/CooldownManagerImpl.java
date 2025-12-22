package net.mokus.mokuslib.cooldown;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.mokus.mokuslib.components.ModComponents;

public class CooldownManagerImpl implements  CooldownManager{
    @Override
    public void addCooldown(PlayerEntity player, Item item, int addedCooldown, int maxCooldown) {
        ModComponents.MOKUS_COOLDOWN.maybeGet(player)
                .ifPresent(c -> c.addCooldown(item, addedCooldown, maxCooldown));
    }

    @Override
    public int getCurrentCooldown(PlayerEntity player, Item item) {
        return ModComponents.MOKUS_COOLDOWN.maybeGet(player)
                .map(c -> c.getCurrentCooldown(item))
                .orElse(0);
    }

    @Override
    public boolean canUse(PlayerEntity player, Item item) {
        return ModComponents.MOKUS_COOLDOWN.maybeGet(player)
                .map(c -> c.canUse(item))
                .orElse(true);
    }
}
