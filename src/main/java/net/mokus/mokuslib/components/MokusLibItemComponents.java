package net.mokus.mokuslib.components;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.MokusLib;

public class MokusLibItemComponents {

    public static final ComponentType<Integer> MOKUS_SKIN_DATA = Registry.register(
            Registries.DATA_COMPONENT_TYPE,
            Identifier.of(MokusLib.MOD_ID, "mokus_skin_data"),
            ComponentType.<Integer>builder().codec(Codec.INT).build()
    );

    public static void init(){
    }
}
