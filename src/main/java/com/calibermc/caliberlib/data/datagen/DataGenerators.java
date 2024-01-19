package com.calibermc.caliberlib.data.datagen;

import com.calibermc.caliberlib.CaliberLib;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CaliberLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    /*@SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        boolean run = false;

        // BlockStates, Loot and Models
        generator.addProvider(run, new ModBlockStateProvider(generator, CaliberLib.MOD_ID, existingFileHelper));
        generator.addProvider(run, new ModLootTableProvider(generator));
        generator.addProvider(run, new ModItemModelProvider(generator, CaliberLib.MOD_ID, existingFileHelper));

    }*/
}
