package com.caedis.duradisplay.mixins.mekanism;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.caedis.duradisplay.overlay.OverlayTanks;
import com.caedis.duradisplay.render.DurabilityRenderer;

import mekanism.common.item.armor.ItemMekaSuitHelmet;

@Mixin(value = ItemMekaSuitHelmet.class, remap = false)
public class MixinItemMekaSuitHelmet {

    @Inject(method = "renderItemOverlayIntoGUI", at = @At("HEAD"), cancellable = true)
    public void renderItemOverlayIntoGUI(ItemStack stack, int xPosition, int yPosition,
                                         CallbackInfoReturnable<Boolean> ci) {
        if (DurabilityRenderer.Execute && OverlayTanks.enabled) {
            ci.setReturnValue(false);
        }
    }
}
