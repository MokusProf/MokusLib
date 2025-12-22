package net.mokus.mokuslib.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.MokusLib;
import net.mokus.mokuslib.components.MokusLibItemComponents;

public class MokusLibItem {

    public static Item DIVINE_GLIDENCE;
    public static Item MOKUS_SWORD;

    private static Item registerItem(String name, Item item, RegistryKey<ItemGroup> group){
        Item registeredItem = Registry.register(Registries.ITEM, Identifier.of(MokusLib.MOD_ID, name), item);
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(registeredItem));
        return registeredItem;
    }


    public static void registerModItems(){
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            MOKUS_SWORD = registerItem("mokus_sword",
                    new SkinTestItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1).component(MokusLibItemComponents.MOKUS_SKIN_DATA,0)),
                    ItemGroups.COMBAT);

            DIVINE_GLIDENCE = registerItem("divine_glidence",
                    new CooldownTestItem(ToolMaterials.DIAMOND, new Item.Settings().maxCount(1)),
                    ItemGroups.COMBAT);
        }
    }
}
