package net.mokus.mokuslib.components;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.MokusLib;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class ModComponents implements EntityComponentInitializer {

    public static final ComponentKey<CooldownComponent> MOKUS_COOLDOWN =
            ComponentRegistry.getOrCreate(
                    Identifier.of(MokusLib.MOD_ID, "mokus_cooldown"),
                    CooldownComponent.class
            );

    public static final ComponentKey<LastDamageDealtComponent> LAST_DAMAGE_DEALT =
            ComponentRegistry.getOrCreate(
                    Identifier.of(MokusLib.MOD_ID, "last_damage_dealt"),
                    LastDamageDealtComponent.class
            );


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MOKUS_COOLDOWN, CooldownComponent::new, RespawnCopyStrategy.NEVER_COPY);
        registry.registerFor(LivingEntity.class, LAST_DAMAGE_DEALT, LastDamageDealtComponent::new);
    }
}
