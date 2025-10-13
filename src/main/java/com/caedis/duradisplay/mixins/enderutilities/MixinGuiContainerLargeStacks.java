package com.caedis.duradisplay.mixins.enderutilities;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.caedis.duradisplay.config.DuraDisplayConfig;
import com.caedis.duradisplay.render.DurabilityRenderer;

import fi.dy.masa.enderutilities.gui.client.base.GuiContainerLargeStacks;

@Mixin(GuiContainerLargeStacks.class)
public class MixinGuiContainerLargeStacks {

    @Redirect(method = "renderLargeStackItemOverlayIntoGUI",
              at = @At(value = "INVOKE",
                       target = "Lnet/minecraft/item/Item;showDurabilityBar(Lnet/minecraft/item/ItemStack;)Z"),
              remap = false)
    private boolean showDurabilityBar(Item instance, ItemStack itemStack, FontRenderer fontRenderer, ItemStack stack,
                                      int xPosition, int yPosition) {
        var item = stack.getItem();
        assert item != null;
        if (!DurabilityRenderer.Execute) return item.showDurabilityBar(stack);
        if (!DuraDisplayConfig.Enable) return item.showDurabilityBar(stack);

        DurabilityRenderer.Render(fontRenderer, stack, xPosition, yPosition);
        return false;
    }
}
