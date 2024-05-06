package com.calibermc.caliberlib.event;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.properties.ModWoodType;
import net.minecraft.client.renderer.Sheets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;


@Mod.EventBusSubscriber(modid = CaliberLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEventBus {

    @SubscribeEvent
    public static void clientSetup(final FMLClientSetupEvent event) {

        // Set Wood Types
        Sheets.addWoodType(ModWoodType.ALPHA);
        Sheets.addWoodType(ModWoodType.BAOBAB);
        Sheets.addWoodType(ModWoodType.BLACKWOOD);
        Sheets.addWoodType(ModWoodType.BLUE_BIOSHROOM);
        Sheets.addWoodType(ModWoodType.BRIMWOOD);
        Sheets.addWoodType(ModWoodType.CEDAR);
        Sheets.addWoodType(ModWoodType.COBALT);
        Sheets.addWoodType(ModWoodType.CYPRESS);
        Sheets.addWoodType(ModWoodType.DEAD);
        Sheets.addWoodType(ModWoodType.EUCALYPTUS);
        Sheets.addWoodType(ModWoodType.FIR);
        Sheets.addWoodType(ModWoodType.GREEN_BIOSHROOM);
        Sheets.addWoodType(ModWoodType.HELLBARK);
        Sheets.addWoodType(ModWoodType.JACARANDA);
        Sheets.addWoodType(ModWoodType.JOSHUA);
        Sheets.addWoodType(ModWoodType.KAPOK);
        Sheets.addWoodType(ModWoodType.LARCH);
        Sheets.addWoodType(ModWoodType.MAGIC);
        Sheets.addWoodType(ModWoodType.MAGNOLIA);
        Sheets.addWoodType(ModWoodType.MAHOGANY);
        Sheets.addWoodType(ModWoodType.MAPLE);
        Sheets.addWoodType(ModWoodType.MAUVE);
        Sheets.addWoodType(ModWoodType.PALM);
        Sheets.addWoodType(ModWoodType.PINE);
        Sheets.addWoodType(ModWoodType.PINK_BIOSHROOM);
        Sheets.addWoodType(ModWoodType.REDWOOD);
        Sheets.addWoodType(ModWoodType.SOCOTRA);
        Sheets.addWoodType(ModWoodType.UMBRAN);
        Sheets.addWoodType(ModWoodType.WILLOW);
        Sheets.addWoodType(ModWoodType.YELLOW_BIOSHROOM);

    }

    @Mod.EventBusSubscriber(modid = CaliberLib.MOD_ID, value = Dist.CLIENT)
    public static class Client {

    }
}