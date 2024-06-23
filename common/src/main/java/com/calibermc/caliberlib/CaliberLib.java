package com.calibermc.caliberlib;

import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.block.properties.BlockProps;
import com.calibermc.caliberlib.dynamicpack.ClientDynamicResourcesHandler;
import com.calibermc.caliberlib.dynamicpack.ServerDynamicResourcesHandler;
import com.calibermc.caliberlib.util.block.BlockManagerRegistry;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.mehvahdjukaar.moonlight.api.misc.Registrator;
import net.mehvahdjukaar.moonlight.api.platform.PlatHelper;
import net.mehvahdjukaar.moonlight.api.set.BlockSetAPI;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Supplier;

import static com.calibermc.caliberlib.block.management.ModBlockHelper.STONE_VARIANTS_WITHOUT_SLAB_STAIRS;

public class CaliberLib {
    public static final String MOD_ID = "caliberlib";
    public static final Logger LOGGER = LogManager.getLogger();

    //public static final BlockManager STONE = BlockManager.register("stone", MOD_ID, BlockProps.LIMESTONE.get(), () -> Blocks.STONE, STONE_VARIANTS_WITHOUT_SLAB_STAIRS).build();

    public static void init() {
        if (PlatHelper.getPhysicalSide().isClient()) {
            CaliberLibClient.init();
        }
        BlockSetAPI.registerBlockSetDefinition(new BlockManagerRegistry("block_manager"));

        BlockSetAPI.addDynamicBlockRegistration(CaliberLib::registerBlocks, BlockManager.class);
        BlockSetAPI.addDynamicRegistration(CaliberLib::registerItems, BlockManager.class, BuiltInRegistries.ITEM);


        ServerDynamicResourcesHandler.registerResourceHandler(CaliberLib.MOD_ID);
        if (PlatHelper.getPhysicalSide().isClient()) {
            ClientDynamicResourcesHandler.registerResourceHandler(CaliberLib.MOD_ID);
        }
        //RegHelper.addItemsToTabsRegistration(CaliberLib::addItemsToTabs);
    }

    private static void registerItems(Registrator<Item> itemRegistrator, Collection<BlockManager> types) {
        for (List<BlockManager> value : BlockManager.BLOCK_MANAGERS.values()) {
            for (BlockManager blockManager : value) {
                for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : blockManager.getBlocks().entrySet()) {
                    Item item = e.getKey().itemSupplier.apply(blockManager, e.getValue().getSecond().get());
                    if (item != null) {
                        itemRegistrator.register(e.getValue().getFirst(), item);
                    }
                }
            }
        }
    }

    private static void registerBlocks(Registrator<Block> blockRegistrator, Collection<BlockManager> types) {
        for (List<BlockManager> value : BlockManager.BLOCK_MANAGERS.values()) {
            for (BlockManager blockManager : value) {
                blockManager.register(blockRegistrator);
            }
        }
    }

    /*private static void addItemsToTabs(RegHelper.ItemToTabEvent event) {
        for (List<BlockManager> value : BlockManager.BLOCK_MANAGERS.values()) {
            for (BlockManager blockManager : value) {
                for (Pair<ResourceLocation, Supplier<Block>> e : blockManager.getBlocks().values()) {
                    event.add(CreativeModeTabs.BUILDING_BLOCKS, blockManager.mainChild().asItem());
                    event.addAfter(CreativeModeTabs.BUILDING_BLOCKS, i -> i.is(blockManager.mainChild().asItem()), e.getSecond().get().asItem());
                }
            }
        }
    }*/

}
