package com.caedis.duradisplay.overlay;

import com.hbm.api.energymk2.IBatteryItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import org.jetbrains.annotations.NotNull;

import com.caedis.duradisplay.DuraDisplay;
import com.caedis.duradisplay.config.ConfigDurabilityLike;
import com.caedis.duradisplay.utils.ColorType;
import com.caedis.duradisplay.utils.DurabilityFormatter;
import com.caedis.duradisplay.utils.DurabilityLikeInfo;
import com.denfop.api.item.IEnergyItem;

import appeng.api.implementations.items.IAEItemPowerStorage;
import gregtech.api.capability.GregtechCapabilities;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import xyz.phanta.tconevo.capability.EuStore;
import xyz.phanta.tconevo.init.TconEvoCaps;

public class OverlayCharge extends OverlayDurabilityLike {

    public static boolean enabled;

    public OverlayCharge() {
        super(
                new ConfigDurabilityLike(
                        true,
                        OverlayDurabilityLike.Style.Text,
                        DurabilityFormatter.Format.percent,
                        8,
                        true,
                        true,
                        0xFF55FF,
                        ColorType.Smooth,
                        new double[] { 30, 70 },
                        new int[] { 0xFFB9AA, 0xBDD6FF, 0x55FFFF },
                        true,
                        2,
                        true) {

                    @Override
                    public void postLoadConfig() {
                        OverlayCharge.enabled = enabled;
                        configCategory.setComment("""
                                Charge is the module that shows charge(Electricity/Power) of items
                                GT EU, IC2 EU, RF included
                                                                            """);
                    }

                    @Override
                    public @NotNull String category() {
                        return "charge";
                    }
                });
        addHandler("com.hbm.api.energymk2.IBatteryItem", OverlayCharge::handleIBatteryItem);
        addHandler("com.denfop.api.item.IEnergyItem", OverlayCharge::handleIEnergyItem);
        addHandler("ic2.api.item.IElectricItem", OverlayCharge::handleIElectricItem);
        addHandler("gregtech.api.items.metaitem.MetaItem", OverlayCharge::handleIElectricItemGT);
        addHandler("gregtech.api.items.toolitem.IGTTool", OverlayCharge::handleIElectricItemGT);
        addHandler("gregtech.api.items.armor.IArmorItem", OverlayCharge::handleIElectricItemGT);
        addHandler("appeng.api.implementations.items.IAEItemPowerStorage", OverlayCharge::handleIAEItemPowerStorage);
        addHandler("slimeknights.tconstruct.library.tools.TinkerToolCore", OverlayCharge::handleTinkersEvo);
        addHandler("net.minecraft.item.Item", OverlayCharge::handleEnergyStorage); // Needs to be last because else all
        // other Handler won't apply
    }

    @Override
    @NotNull
    ConfigDurabilityLike config() {
        return config;
    }

    public static DurabilityLikeInfo handleIElectricItem(@NotNull ItemStack stack) {
        IElectricItem bei = ((IElectricItem) stack.getItem());
        assert bei != null;

        return new DurabilityLikeInfo(ElectricItem.manager.getCharge(stack), bei.getMaxCharge(stack));
    }

    public static DurabilityLikeInfo handleEnergyStorage(@NotNull ItemStack stack) {
        IEnergyStorage eci = stack.getCapability(CapabilityEnergy.ENERGY, null);

        if (eci != null) return new DurabilityLikeInfo(eci.getEnergyStored(), eci.getMaxEnergyStored());
        return null;
    }

    public static DurabilityLikeInfo handleIElectricItemGT(@NotNull ItemStack stack) {
        gregtech.api.capability.IElectricItem electricItem = stack
                .getCapability(GregtechCapabilities.CAPABILITY_ELECTRIC_ITEM, null);
        if (electricItem != null) {
            return new DurabilityLikeInfo(electricItem.getCharge(), electricItem.getMaxCharge());
        }
        return null;
    }

    public static DurabilityLikeInfo handleIAEItemPowerStorage(@NotNull ItemStack stack) {
        IAEItemPowerStorage tool = ((IAEItemPowerStorage) stack.getItem());
        assert tool != null;
        return new DurabilityLikeInfo(tool.getAECurrentPower(stack), tool.getAEMaxPower(stack));
    }

    public static DurabilityLikeInfo handleTinkersEvo(@NotNull ItemStack stack) {
        if (stack.hasCapability(CapabilityEnergy.ENERGY, null)) {
            return handleEnergyStorage(stack);
        }
        if (DuraDisplay.ic2Loaded) {
            if (stack.hasCapability(TconEvoCaps.EU_STORE, null)) {
                EuStore euStore = stack.getCapability(TconEvoCaps.EU_STORE, null);
                assert euStore != null;
                return new DurabilityLikeInfo(euStore.getEuStored(), euStore.getEuStoredMax());
            }
        }
        return null;
    }

    public static DurabilityLikeInfo handleIEnergyItem(@NotNull ItemStack stack) {
        IEnergyItem electricItem = ((IEnergyItem) stack.getItem());
        assert electricItem != null;
        return new DurabilityLikeInfo(com.denfop.ElectricItem.manager.getCharge(stack),
                electricItem.getMaxEnergy(stack));
    }

    public static DurabilityLikeInfo handleIBatteryItem(@NotNull ItemStack stack) {
        IBatteryItem batteryItem = ((IBatteryItem) stack.getItem());
        assert batteryItem != null;
        return new DurabilityLikeInfo(batteryItem.getCharge(stack), batteryItem.getMaxCharge(stack));
    }
}
