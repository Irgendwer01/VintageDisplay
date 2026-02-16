package com.caedis.duradisplay.overlay;

import net.minecraft.item.ItemStack;

import org.jetbrains.annotations.NotNull;

import com.caedis.duradisplay.config.ConfigDurabilityLike;
import com.caedis.duradisplay.utils.ColorType;
import com.caedis.duradisplay.utils.DurabilityFormatter;
import com.caedis.duradisplay.utils.DurabilityLikeInfo;

import vazkii.botania.api.mana.IManaItem;

// Overlay for anything magic alike like Botania Mana
@SuppressWarnings("unused")
public class OverlayMagic extends OverlayDurabilityLike {

    public OverlayMagic() {
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
                                Magic is the module that shows magical charge (or whatever) of items
                                                                             """);
                    }

                    @Override
                    public @NotNull String category() {
                        return "magic";
                    }
                });
        addHandler("vazkii.botania.api.mana.IManaItem", OverlayMagic::handleBotania);
    }

    @Override
    public @NotNull ConfigDurabilityLike config() {
        return config;
    }

    public static DurabilityLikeInfo handleBotania(ItemStack stack) {
        IManaItem manaItem = (IManaItem) stack.getItem();
        assert manaItem != null;
        return new DurabilityLikeInfo(manaItem.getMana(stack), manaItem.getMaxMana(stack));
    }
}
