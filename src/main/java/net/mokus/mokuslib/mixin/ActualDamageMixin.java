package net.mokus.mokuslib.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.mokus.mokuslib.components.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class ActualDamageMixin {
    @Unique
    private float healthBefore = 0f;

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"))
    private void captureHealthBefore(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity victim = (LivingEntity) (Object) this;
        this.healthBefore = victim.getHealth();
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V", shift = At.Shift.AFTER))
    private void captureFinalDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity victim = (LivingEntity) (Object) this;
        if (source.getAttacker() != null) {
            ModComponents.LAST_DAMAGE_DEALT.get(victim).setResDamageGotten(this.healthBefore - victim.getHealth());
            ModComponents.LAST_DAMAGE_DEALT.get(victim).setLastDamageGotten(amount);
        }
    }

}
