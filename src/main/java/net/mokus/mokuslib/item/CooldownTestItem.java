package net.mokus.mokuslib.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.mokus.mokuslib.cooldown.MokusLibCooldownItem;
import net.mokus.mokuslib.itemskin.CustomItemModel;

public class CooldownTestItem extends SwordItem implements MokusLibCooldownItem, CustomItemModel {

    public CooldownTestItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public int getMaxCooldown(ItemStack item) {
        return 240;
    }

    @Override
    public boolean hasInventoryModel() {
        return false;
    }

    @Override
    public int getAddedCooldown(ItemStack item) {
        return 60;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!checkCooldown(user, this)) {
            return TypedActionResult.fail(itemStack);
        }
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof PlayerEntity playerEntity)) return;

        float f = playerEntity.getYaw();
        float g = playerEntity.getPitch();
        float h = -MathHelper.sin(f * ((float) Math.PI / 180.0F)) * MathHelper.cos(g * ((float) Math.PI / 180.0F));
        float k = -MathHelper.sin(g * ((float) Math.PI / 180.0F));
        float l = MathHelper.cos(f * ((float) Math.PI / 180.0F)) * MathHelper.cos(g * ((float) Math.PI / 180.0F));
        float m = MathHelper.sqrt(h * h + k * k + l * l);
        float n = 3.0F * ((1.0F * 2) / 4.0F);
        h *= n / m;
        k *= n / m;
        l *= n / m;
        playerEntity.addVelocity(h, k, l);
        playerEntity.useRiptide(40,0.0f,stack);
        applyCooldown(playerEntity,stack);
    }
}
