package net.mokus.mokuslib;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.api.MokusLibClientAPI;


public class MokusLibClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        MokusLibClientAPI.registerItemModels(
                Identifier.of(MokusLib.MOD_ID, "mokus_sword_inv"),
                Identifier.of(MokusLib.MOD_ID,"mokus_sword_alt_1"),
                Identifier.of(MokusLib.MOD_ID,"mokus_sword_alt_1_inv")
        );
    }
}