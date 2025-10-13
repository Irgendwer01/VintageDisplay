package com.caedis.duradisplay.mixins.sussypatches;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.caedis.duradisplay.overlay.OverlayCharge;
import com.caedis.duradisplay.render.DurabilityRenderer;

import dev.tianmi.sussypatches.api.event.RenderItemOverlayEvent;
import dev.tianmi.sussypatches.common.helper.FluidBarRenderer;

@Mixin(FluidBarRenderer.class)
public class MixinFluidBarRenderer {

    @Inject(method = "onRenderItemOverlayEvent", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRenderItemOverlayEvent(RenderItemOverlayEvent event, CallbackInfo ci) {
        if (DurabilityRenderer.Execute && OverlayCharge.enabled) {
            ci.cancel();
        }
    }
}
