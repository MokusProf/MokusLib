package net.mokus.mokuslib.api;

import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class MokusLibClientAPI {
    private static final Set<ModelIdentifier> EXTRA_ITEM_MODELS = new HashSet<>();

    public static void registerItemModel(Identifier modelId) {
        EXTRA_ITEM_MODELS.add(ModelIdentifier.ofInventoryVariant(modelId));
    }

    public static void registerItemModels(Identifier... modelIds) {
        for (Identifier id : modelIds) {
            EXTRA_ITEM_MODELS.add(ModelIdentifier.ofInventoryVariant(id));
        }
    }


    public static Collection<ModelIdentifier> getExtraModels() {
        return EXTRA_ITEM_MODELS;
    }
}
