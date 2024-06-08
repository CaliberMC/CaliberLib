package com.calibermc.caliberlib.forge.data;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.forge.CaliberLibForge;
import com.calibermc.caliberlib.forge.data.datagen.ModBlockStateProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = CaliberLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    // TODO: Add Fusion Data Generators for connected textures in Create Blocks

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        boolean run = true;
        CaliberLibForge.genData();

        generator.addProvider(event.includeClient(), new ModBlockStateProvider(generator, CaliberLib.MOD_ID, existingFileHelper));
    }
}
