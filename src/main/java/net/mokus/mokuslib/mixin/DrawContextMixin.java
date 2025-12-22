package net.mokus.mokuslib.mixin;


import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.mokus.mokuslib.item.MokusLibCooldownItem;
import net.mokus.mokuslib.components.ModComponents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Shadow @Final
    private MinecraftClient client;
    @Final
    @Shadow
    private MatrixStack matrices;

    @Shadow public abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color);

    @Inject(method = "drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"))
    private void mokuslib$drawItemInSlotHook(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        if (stack.isEmpty()) return;
        if (client.isPaused()) return;

        if (stack.getItem() instanceof MokusLibCooldownItem CooldownItem) {
            assert client.player != null;
            int cooldown = ModComponents.MOKUS_COOLDOWN.get(client.player).getCurrentCooldown(stack.getItem());
            int maxCooldown = CooldownItem.getMaxCooldown(stack);

            if (cooldown > 0) {
                float progress = MathHelper.clamp((float) cooldown / maxCooldown, 0f, 1f);
                int top = y + MathHelper.floor(16f * (1f - progress));
                int bottom = y + 16;
                matrices.push();
                this.fill(RenderLayer.getGuiOverlay(), x, top, x + 16, bottom, 0x80FFFFFF);
                matrices.pop();
            }
        }
    }
}
