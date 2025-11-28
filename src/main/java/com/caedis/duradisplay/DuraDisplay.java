package com.caedis.duradisplay;

import java.util.Collections;
import java.util.List;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caedis.duradisplay.config.DuraDisplayConfig;

import zone.rong.mixinbooter.ILateMixinLoader;

@Mod(
     modid = Tags.MODID,
     version = Tags.VERSION,
     name = Tags.MODNAME,
     acceptedMinecraftVersions = "[1.12.2]",
     guiFactory = "com.caedis.duradisplay.config.GuiFactory",
     acceptableRemoteVersions = "*")
public class DuraDisplay implements ILateMixinLoader {

    public static final Logger LOG = LogManager.getLogger(Tags.MODID);
    public static boolean ic2Loaded = false;
    public static boolean mekanismLoaded = false;
    public static boolean tinkersEvoLoaded = false;

    @SuppressWarnings("unused")
    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        if (FMLCommonHandler.instance()
                .getSide()
                .isClient()) {
            DuraDisplayConfig.loadConfig();
            FMLCommonHandler.instance()
                    .bus()
                    .register(this);
        }
        ic2Loaded = Loader.isModLoaded("ic2");
        mekanismLoaded = Loader.isModLoaded("mekanism");
        tinkersEvoLoaded = Loader.isModLoaded("tconevo");
    }

    @SuppressWarnings("unused")
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Tags.MODID)) {
            DuraDisplayConfig.config.save();
            DuraDisplayConfig.reloadConfigObject();
        }
    }

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.duradisplay-late.json");
    }
}
