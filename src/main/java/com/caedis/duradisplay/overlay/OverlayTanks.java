package com.caedis.duradisplay.overlay;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import org.jetbrains.annotations.NotNull;

import com.caedis.duradisplay.DuraDisplay;
import com.caedis.duradisplay.config.ConfigDurabilityLike;
import com.caedis.duradisplay.utils.ColorType;
import com.caedis.duradisplay.utils.DurabilityFormatter;
import com.caedis.duradisplay.utils.DurabilityLikeInfo;

import mekanism.api.gas.IGasItem;

public class OverlayTanks extends OverlayDurabilityLike {

    public OverlayTanks() {
        super(
                new ConfigDurabilityLike(
                        true,
                        OverlayDurabilityLike.Style.Text,
                        DurabilityFormatter.Format.percent,
                        2,
                        false,
                        true,
                        0x00FF00,
                        ColorType.RYGDurability,
                        new double[] { 30, 70 },
                        new int[] { 0xFF0000, 0x55FF00, 0x00FF00 },
                        true,
                        0,
                        true) {

                    @Override
                    public void postLoadConfig() {
                        configCategory.setComment("""
                                Tanks is the module that shows the current fill level of fluid tanks
                                                                             """);
                    }

                    @Override
                    public @NotNull String category() {
                        return "tanks";
                    }
                });
        addHandler("net.minecraft.item.ItemBucket", i -> null);
        addHandler("net.minecraft.item.Item", OverlayTanks::handleDefault); // Needs to be last because else all
        // other Handler won't apply
    }

    @Override
    public @NotNull ConfigDurabilityLike config() {
        return config;
    }

    public static DurabilityLikeInfo handleDefault(@NotNull ItemStack stack) {
        if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem fluidHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY,
                    null);
            int fill = 0;
            int capacity = 0;
            for (IFluidTankProperties tankProperties : fluidHandler.getTankProperties()) {
                capacity += tankProperties.getCapacity();
                if (tankProperties.getContents() != null)
                    fill += tankProperties.getContents().amount;
            }
            return new DurabilityLikeInfo(fill, capacity);
        }
        if (DuraDisplay.mekanismLoaded) {
            if (stack.getItem() instanceof IGasItem iGasItem) {
                double max = iGasItem.getMaxGas(stack);
                double damage = iGasItem.getGas(stack) == null ? 0 : max - iGasItem.getGas(stack).amount;
                double current = max - damage;
                return new DurabilityLikeInfo(current, max);
            }
        }
        return null;
    }
}
