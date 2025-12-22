package net.mokus.mokuslib.itemskin;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public interface CustomItemModel {

    default int getVariantCount() {
        return 0;
    }

    default String getModelNamespace() {
        return this instanceof Item item
                ? Registries.ITEM.getId(item).getNamespace()
                : "minecraft";
    }

    default String getItemPath() {
        return this instanceof Item item
                ? Registries.ITEM.getId(item).getPath()
                : "unknown_item";
    }

    default Identifier getInventoryModel(int variant) {
        if (variant == 0) return Identifier.of(getModelNamespace(), getItemPath() + "_inv");
        return Identifier.of(getModelNamespace(), getItemPath() + "_alt_" + variant + "_inv");
    }

    default Identifier getHandModel(int variant) {
        if (variant == 0) return null;
        return Identifier.of(getModelNamespace(), getItemPath() + "_alt_" + variant);
    }
}
