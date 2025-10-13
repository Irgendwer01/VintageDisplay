package com.caedis.duradisplay.mixins.tinkersevo;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.caedis.duradisplay.overlay.OverlayCharge;
import com.caedis.duradisplay.render.DurabilityRenderer;

import xyz.phanta.tconevo.client.handler.ItemStackBarCoreHooks;

@Mixin(ItemStackBarCoreHooks.class)
public class MixinItemStackBarCoreHooks {

    @Inject(method = "handleRender", at = @At("HEAD"), remap = false, cancellable = true)
    private static void handleRender(ItemStack stack, int posX, int posY, CallbackInfo ci) {
        if (DurabilityRenderer.Execute && OverlayCharge.enabled) {
            ci.cancel();
        }
    }
}
