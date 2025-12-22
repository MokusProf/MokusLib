package net.mokus.mokuslib.components;

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


    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(MOKUS_COOLDOWN, CooldownComponent::new, RespawnCopyStrategy.NEVER_COPY);
    }
}
