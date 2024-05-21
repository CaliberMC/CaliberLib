package com.calibermc.caliberlib.forge.util;

import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.forge.block.ModBlockHelperForge;
import com.calibermc.caliberlib.forge.data.datagen.ModBlockStateProvider;
import com.calibermc.caliberlib.util.AdditionalDataGen;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AdditionalDataGenForge extends AdditionalDataGen {
    public BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>> stateGenerator = (b, pair) -> {
        ModBlockStateProvider provider = pair.getSecond();
        ModBlockHelperForge.fixBlockTex(b, b, pair, (block, side, bottom, top, tex) ->
                provider.simpleBlock(b.get(), provider.models().cubeBottomTop(provider.name(b.get()), side, bottom, top)), (block, tex) ->
                provider.simpleBlock(b.get(), provider.models().cubeAll(provider.name(b.get()), tex)));
    };

    public AdditionalDataGenForge(BlockManager.BlockAdditional additional) {
        super(additional);
    }

    public AdditionalDataGenForge stateGen(BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>> stateGenerator) {
        this.stateGenerator = stateGenerator;
        return this;
    }
}
