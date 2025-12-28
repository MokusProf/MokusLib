package net.mokus.mokuslib.item;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Formatting;
import net.mokus.mokuslib.components.MokusLibItemComponents;
import net.mokus.mokuslib.itemskin.CustomItemModel;

import java.util.List;

public class SkinTestItem extends SwordItem implements CustomItemModel {

    public SkinTestItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public int getVariantCount() {
        return 2;
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (clickType != ClickType.RIGHT) {
            return false;
        }

        int skin_current = stack.getOrDefault(MokusLibItemComponents.MOKUS_SKIN_DATA,0);
        stack.set(MokusLibItemComponents.MOKUS_SKIN_DATA, (skin_current + 1) % 3);

        slot.markDirty();
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int variant = stack.getOrDefault(MokusLibItemComponents.MOKUS_SKIN_DATA,0);

        String variantName;
        int color = switch (variant) {
            case 1 -> {
                variantName = "Angel";
                yield 0xEFBF04;
            }
            case 2 -> {
                variantName = "ERROR";
                yield 0x702963;
            }
            default -> {
                variantName = "Vendetta";
                yield 0x800020;
            }
        };

        tooltip.add(Text.literal("Skin: ").formatted(Formatting.GRAY)
                .append(Text.literal(variantName).setStyle(Style.EMPTY.withColor(color))));
    }
}
