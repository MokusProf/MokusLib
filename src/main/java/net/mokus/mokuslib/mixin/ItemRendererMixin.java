package net.mokus.mokuslib.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.mokus.mokuslib.components.MokusLibItemComponents;
import net.mokus.mokuslib.itemskin.CustomItemModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel mokuslib$swapModel(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode mode) {
        Item item = stack.getItem();

        if (!(item instanceof CustomItemModel api)) {
            return bakedModel;
        }

        int variant = stack.getComponents().getOrDefault(MokusLibItemComponents.MOKUS_SKIN_DATA,0);
        if (variant < 0 || variant > api.getVariantCount()) {
            return bakedModel;
        }

        Identifier modelId;

        if (mode == ModelTransformationMode.GUI
                || mode == ModelTransformationMode.FIXED
                || mode == ModelTransformationMode.GROUND) {

            modelId = api.getInventoryModel(variant);
        } else {
            modelId = api.getHandModel(variant);
            if (modelId == null) return bakedModel;
        }

        return ((ItemRendererAccessor) this)
                .mokuslib$getModels()
                .getModelManager()
                .getModel(ModelIdentifier.ofInventoryVariant(modelId));
    }
}
