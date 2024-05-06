package com.calibermc.caliberlib.block.properties;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public enum BlockProps {
    ANDESITE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    BASALT(BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT)),
    BLACKSTONE(BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)),
    BRONZE_BLOCK(BlockBehaviour.Properties.of().strength(5f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.BRICKS)),
    CALCITE(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    COBBLESTONE(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    CONCRETE(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE)),
    CLAY(BlockBehaviour.Properties.of().strength(0.6f).sound(SoundType.GRAVEL)),
    DEEPSLATE(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()),
    DEEPSLATE_BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).sound(SoundType.DEEPSLATE_BRICKS).requiresCorrectToolForDrops()),
    DEEPSLATE_TILES(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_TILES).sound(SoundType.DEEPSLATE_TILES).requiresCorrectToolForDrops()),
    COBBLED_DEEPSLATE(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops()),
    DEEPSLATE_SILVER_ORE(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    DEEPSLATE_TIN_ORE(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    DIORITE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    DIRT(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.GRAVEL)),
    DRIPSTONE_BLOCK(BlockBehaviour.Properties.ofFullCopy(Blocks.DRIPSTONE_BLOCK)),
    END_STONE(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE)),
    END_STONE_BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.END_STONE_BRICKS)),
    FARMLAND(BlockBehaviour.Properties.ofFullCopy(Blocks.FARMLAND)),
    GILDED_BLACKSTONE(BlockBehaviour.Properties.ofFullCopy(Blocks.GILDED_BLACKSTONE)),
    GRANITE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    GRASS(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)),
    GRAVEL(BlockBehaviour.Properties.of().strength(0.6f).sound(SoundType.GRAVEL)),
//    HALF_TIMBERED(BlockBehaviour.Properties.of().strength(2.0f, 3.0F).sound(SoundType.WOOD).requiresCorrectToolForDrops()),
    TUDOR_1(BlockBehaviour.Properties.of().strength(2.0f, 3.0F).sound(SoundType.WOOD).requiresCorrectToolForDrops()),
    TUDOR_2(BlockBehaviour.Properties.of().strength(2.0f, 3.0F).sound(SoundType.WOOD).requiresCorrectToolForDrops()),
    LIMESTONE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    LIMESTONE_BRICK(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    MARBLE(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    MUD_BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.MUD_BRICKS)),
    MYCELIUM(BlockBehaviour.Properties.ofFullCopy(Blocks.MYCELIUM)),
    CRIMSON_NYLIUM(BlockBehaviour.Properties.ofFullCopy(Blocks.CRIMSON_NYLIUM)),
    NETHERITE(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)),
    NETHER_BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)),
    OBSIDIAN(BlockBehaviour.Properties.ofFullCopy(Blocks.OBSIDIAN)),
    PLANK(BlockBehaviour.Properties.of().strength(2.0f, 3.0F).sound(SoundType.WOOD)),
    PLANK_NO_OC(BlockBehaviour.Properties.of().strength(2.0f, 3.0F).sound(SoundType.WOOD).noOcclusion()),
    PLASTER(BlockBehaviour.Properties.of().strength(1.5f).sound(SoundType.TUFF).requiresCorrectToolForDrops()),
    PLASTER_POWDER(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.SAND)),
    PODZOL(BlockBehaviour.Properties.ofFullCopy(Blocks.PODZOL)),
    PURPUR(BlockBehaviour.Properties.ofFullCopy(Blocks.PURPUR_BLOCK)),
    POLISHED_ANDESITE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    POLISHED_BASALT(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BASALT)),
    POLISHED_BLACKSTONE(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE)),
    POLISHED_BLACKSTONE_BRICK(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)),
    POLISHED_DEEPSLATE(BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLED_DEEPSLATE).sound(SoundType.POLISHED_DEEPSLATE).requiresCorrectToolForDrops()),
    POLISHED_DIORITE(BlockBehaviour.Properties.of().strength(1.5F, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    PRISMARINE(BlockBehaviour.Properties.ofFullCopy(Blocks.PRISMARINE)),
    QUARTZ(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    RAW_SILVER(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    RAW_TIN(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    SAND(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.SAND).requiresCorrectToolForDrops()),
    SANDSTONE(BlockBehaviour.Properties.of().strength(0.8f).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    SMOOTH_QUARTZ_BLOCK(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    SOUL_SAND(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SAND)),
    SOUL_SOIL(BlockBehaviour.Properties.ofFullCopy(Blocks.SOUL_SOIL)),
    SILVER_BLOCK(BlockBehaviour.Properties.of().strength(5f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    SILVER_ORE(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    SMOOTH_BASALT(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_BASALT)),
    SMOOTH_LIMESTONE(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    SMOOTH_QUARTZ(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    SMOOTH_SANDSTONE(BlockBehaviour.Properties.of().strength(2.0f, 6.0F).sound(SoundType.STONE).requiresCorrectToolForDrops()),
    TERRACOTTA(BlockBehaviour.Properties.ofFullCopy(Blocks.TERRACOTTA)),
    THATCH(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.GRASS)),
    TIN_BLOCK(BlockBehaviour.Properties.of().strength(5f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    TIN_ORE(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.METAL).requiresCorrectToolForDrops()),
    TREE_LEAVES(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)),
    TREE_LOG(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)),
    TREE_SAPLING(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)),
    TUFF(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)),
    WARPED_NYLIUM(BlockBehaviour.Properties.ofFullCopy(Blocks.WARPED_NYLIUM)),
    WOOD(BlockBehaviour.Properties.of().strength(2.0f).sound(SoundType.WOOD)),
    WOOL(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL));

    private final BlockBehaviour.Properties properties;

    BlockProps(BlockBehaviour.Properties properties) {
        this.properties = properties;
    }

    public BlockBehaviour.Properties get() {
        return properties;
    }
}
