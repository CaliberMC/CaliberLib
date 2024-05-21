package com.calibermc.caliberlib.util.block;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.google.common.base.Stopwatch;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.set.BlockTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.*;

public class BlockManagerRegistry extends BlockTypeRegistry<BlockManager> {
    public static final BlockManager STONE_TYPE = BlockManager.registerOneBlock("stone", "minecraft", () -> Blocks.STONE, true).build();

    public BlockManagerRegistry(String name) {
        super(BlockManager.class, name);
    }

    @Override
    public BlockManager getDefaultType() {
        return STONE_TYPE;
    }

    @Override
    public Optional<BlockManager> detectTypeFromBlock(Block block, ResourceLocation baseRes) {
        for (Map.Entry<String, List<BlockManager>> e : BlockManager.BLOCK_MANAGERS.entrySet()) {
            for (BlockManager blockManager : e.getValue()) {
                if (baseRes.equals(BuiltInRegistries.BLOCK.getKey(blockManager.mainChild()))) {
                    return Optional.of(blockManager);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public void buildAll() {
        Stopwatch watch = Stopwatch.createStarted();
        super.buildAll();
        CaliberLib.LOGGER.info("Initialized block managers in: {} ms", watch.elapsed().toMillis());
    }

    @Override
    public void addTypeTranslations(AfterLanguageLoadEvent language) {
        this.getValues().forEach((w) -> {
            if (language.isDefault()) language.addEntry(w.getTranslationKey(), w.getReadableName());
        });
    }
}
