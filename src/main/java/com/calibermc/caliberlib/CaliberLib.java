package com.calibermc.caliberlib;

import net.minecraft.world.inventory.RecipeBookType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("caliberlib")
public class CaliberLib {
    public static final String MOD_ID = "caliberlib";

    private static final Logger LOGGER = LogManager.getLogger();

    public CaliberLib() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loading Caliber Lib");
    }
}
