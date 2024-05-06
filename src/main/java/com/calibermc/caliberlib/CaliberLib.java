package com.calibermc.caliberlib;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CaliberLib.MOD_ID)
public class CaliberLib
{

    public static final String MOD_ID = "caliberlib";

    private static final Logger LOGGER = LogUtils.getLogger();

    public CaliberLib(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Loading Caliber Lib");
    }
}
