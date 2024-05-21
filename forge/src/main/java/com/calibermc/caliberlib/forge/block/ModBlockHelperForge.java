package com.calibermc.caliberlib.forge.block;

import com.calibermc.caliberlib.block.custom.*;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.forge.data.datagen.ModBlockStateProvider;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;

import java.util.function.*;

import static com.calibermc.caliberlib.data.ModBlockFamily.Variant;

public class ModBlockHelperForge {

    // Asset & Data Generation presets
    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ARCH = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<ArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> provider.getSecond().archBlock(block, side, bottom, top), provider.getSecond()::archBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ARCH_HALF = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<HalfArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().archHalfBlock(block, side, bottom, top), provider.getSecond()::archHalfBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ARCH_LARGE = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<LargeArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().archLargeBlock(block, side, bottom, top), provider.getSecond()::archLargeBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ARCH_LARGE_HALF = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<LargeHalfArchBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().archLargeHalfBlock(block, side, bottom, top), provider.getSecond()::archLargeHalfBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ARROWSLIT = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<ArrowSlitBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().arrowslitBlock(block, side, bottom, top), provider.getSecond()::arrowslitBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BALUSTRADE = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<BalustradeBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().balustradeBlock(block, side, bottom, top), provider.getSecond()::balustradeBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BEAM_DIAGONAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<DiagonalBeamBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().beamDiagonalBlock(block, tex), provider.getSecond()::beamDiagonalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BEAM_HORIZONTAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<HorizontalBeamBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().beamHorizontalBlock(block, tex), provider.getSecond()::beamHorizontalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BEAM_LINTEL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<BeamLintelBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().beamLintelBlock(block, side, bottom, top), provider.getSecond()::beamLintelBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BEAM_POSTS = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<BeamPostsBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().beamPostsBlock(block, tex), provider.getSecond()::beamPostsBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BEAM_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<VerticalBeamBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().beamVerticalBlock(block, tex), provider.getSecond()::beamVerticalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> BUTTON = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<ButtonBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.getSecond().buttonBlock(block, top);
                provider.getSecond().models().buttonInventory(provider.getSecond().name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.getSecond().buttonBlock(block, texture);
                provider.getSecond().models().buttonInventory(provider.getSecond().name(block) + "_inventory", texture);
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> CAPITAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<CapitalBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().capitalBlock(block, side, bottom, top), provider.getSecond()::capitalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> CORNER = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<CornerLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::cornerLayerBlock, provider.getSecond()::cornerLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> CORNER_SLAB = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<CornerSlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::cornerSlabLayerBlock, provider.getSecond()::cornerSlabLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> CORNER_SLAB_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<VerticalCornerSlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::cornerSlabLayerVerticalBlock, provider.getSecond()::cornerSlabLayerVerticalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> DOOR = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<DoorBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().doorBlock(block, bottom, top), (block, texture) -> {
                ResourceLocation loc = provider.getSecond().blockTexture(block);
                provider.getSecond().doorBlock(block, loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> TALL_DOOR = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<TallDoorBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().tallDoorBlock(block, bottom, side, top), (block, texture) -> {
                ResourceLocation loc = provider.getSecond().blockTexture(block);
                provider.getSecond().tallDoorBlock(block, loc.withPath(loc.getPath() + "_bottom"), loc.withPath(loc.getPath() + "_middle"), loc.withPath(loc.getPath() + "_top"));
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> DOOR_FRAME = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<DoorFrameBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().doorFrameBlock(block, tex), provider.getSecond()::doorFrameBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> DOOR_FRAME_LINTEL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<DoorFrameLintelBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().doorFrameLintelBlock(block, tex), provider.getSecond()::doorFrameLintelBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> EIGHTH = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<EighthLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::eighthLayerBlock, provider.getSecond()::eighthLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> FENCE = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<FenceBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.getSecond().fenceBlock(block, top);
                provider.getSecond().models().fenceInventory(provider.getSecond().name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.getSecond().fenceBlock(block, texture);
                provider.getSecond().models().fenceInventory(provider.getSecond().name(block) + "_inventory", texture);
            });

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> FENCE_GATE = (textureFrom) -> (b, provider) ->
            provider.getSecond().fenceGateBlock((FenceGateBlock) b.get(), provider.getSecond().blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>> HANGING_SIGN = (s, pair) -> {
        ModBlockStateProvider provider = pair.getSecond();
        Block sign = pair.getFirst().get(Variant.HANGING_SIGN);
        Block wall = pair.getFirst().get(Variant.WALL_HANGING_SIGN);
        provider.hangingSignBlock(sign, wall, provider.blockTexture(BlockManager.getMainBy(s, () -> sign)));
    };

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> LAYER = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<SlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::slabLayerBlock, provider.getSecond()::slabLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> LAYER_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<VerticalSlabLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::slabVerticalLayerBlock, provider.getSecond()::slabVerticalLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> PILLAR = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<PillarLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::pillarLayerBlock, provider.getSecond()::pillarLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> PRESSURE_PLATE = (textureFrom) -> (b, provider) ->
            provider.getSecond().pressurePlateBlock((PressurePlateBlock) b.get(), provider.getSecond().blockTexture(BlockManager.getMainBy(b, textureFrom)));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> TRAP_DOOR = (textureFrom) -> (b, provider) ->
            provider.getSecond().trapdoorBlock((TrapDoorBlock) b.get(), provider.getSecond().blockTexture(BlockManager.getMainBy(b, textureFrom)), true);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> QUARTER = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<QuarterLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::quarterLayerBlock, provider.getSecond()::quarterLayerBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> VERTICAL_QUARTER = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<VerticalQuarterLayerBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::quarterLayerVerticalBlock, provider.getSecond()::quarterLayerVerticalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ROOF_22 = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<Roof22Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().roof22Block(block, tex), provider.getSecond()::roof22Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ROOF_45 = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<Roof45Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().roof45Block(block, tex), provider.getSecond()::roof45Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ROOF_67 = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<Roof67Block>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().roof67Block(block, tex), provider.getSecond()::roof67Block);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> ROOF_PEAK = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<RoofPeakBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().roofPeakBlock(block, tex), provider.getSecond()::roofPeakBlock);

    public static final BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>> SIGN = (s, pair) -> {
        ModBlockStateProvider provider = pair.getSecond();
        StandingSignBlock sign = (StandingSignBlock) pair.getFirst().get(Variant.SIGN);
        WallSignBlock wall = (WallSignBlock) pair.getFirst().get(Variant.WALL_SIGN);
        provider.signBlock(sign, wall, provider.blockTexture(BlockManager.getMainBy(s, () -> sign)));
    };

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> SLAB = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<SlabBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.getSecond().slabBlock(block, tex, side, bottom, top);
                provider.getSecond().models().slab(provider.getSecond().name(block), side, bottom, top);
                provider.getSecond().models().slab(provider.getSecond().name(block) + "_top", side, bottom, top);
            }, (block, texture) -> provider.getSecond().slabBlock(block, texture, texture, texture, texture));

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> SLAB_VERTICAL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<VerticalSlabBlock>fixBlockTex(textureFrom, b, provider, provider.getSecond()::slabVerticalBlock, provider.getSecond()::slabVerticalBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> STAIRS = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<StairBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().stairsBlock(block, side, bottom, top), provider.getSecond()::stairsBlock);

    public static final Function<Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> WALL = (textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<WallBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> {
                provider.getSecond().wallBlock(block, top);
                provider.getSecond().models().wallInventory(provider.getSecond().name(block) + "_inventory", top);
            }, (block, texture) -> {
                provider.getSecond().wallBlock(block, texture);
                provider.getSecond().models().wallInventory(provider.getSecond().name(block) + "_inventory", texture);
            });

    public static final BiFunction<String, Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> WINDOW = (s, textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<WindowBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) ->
                    provider.getSecond().windowBlock(block, side, bottom, top), provider.getSecond()::windowBlock);

    public static final BiFunction<String, Supplier<Block>, BiConsumer<Supplier<Block>, Pair<BlockManager, ModBlockStateProvider>>> WINDOW_HALF = (s, textureFrom) -> (b, provider) ->
            ModBlockHelperForge.<HalfWindowBlock>fixBlockTex(textureFrom, b, provider, (block, side, bottom, top, tex) -> provider.getSecond().windowHalfBlock(block, side, bottom, top), provider.getSecond()::windowHalfBlock);

    @SuppressWarnings("unchecked")
    public static <T extends Block> void fixBlockTex(Supplier<Block> textureFrom, Supplier<Block> b, Pair<BlockManager, ModBlockStateProvider> pair, BlockGenLayerWithSides<T> genWithSides, BlockGenWithBaseBlock<T> gen) {
        ResourceLocation tex = pair.getSecond().blockTexture(BlockManager.getMainBy(b, textureFrom));
        ResourceLocation top, bottom;
        ResourceLocation side = tex;
        String originalPath = tex.getPath();
        String modifiedPath = tex.getPath();
        boolean isMinecraftNamespace = tex.getNamespace().equals("minecraft");

        if (tex.getPath().contains("_wood") && !tex.getPath().contains("stained") || tex.getPath().contains("_hyphae")) {
            String replacement = tex.getPath().contains("_wood") ? "_wood" : "_hyphae";
            String newTexture = tex.getPath().contains("_wood") ? "_log" : "_stem";
            tex = new ResourceLocation(tex.getNamespace(), tex.getPath().replace(replacement, newTexture));
            genWithSides.data((T) b.get(), tex, tex, tex, tex);

        } else if (tex.getPath().contains("bamboo_block") && !tex.getPath().contains("stained")) {
            tex = new ResourceLocation(tex.getNamespace(), "block/stripped_bamboo_block");
            genWithSides.data((T) b.get(), tex, tex, tex, tex);

        } else if (tex.getPath().contains("tudor")) {
            String woodType = extractWoodType(tex);
            String prefix = tex.getPath().contains("stained") ? "stained_" : "";
            top = bottom = new ResourceLocation(tex.getNamespace(), "block/" + prefix + woodType + "_boards");
            genWithSides.data((T) b.get(), side, bottom, top, tex);

        } else if (tex.getPath().equals("block/basalt") || tex.getPath().equals("block/polished_basalt") || (tex.getPath().contains("quartz") && !tex.getPath().contains("brick"))) {
            side = new ResourceLocation(tex.getNamespace(), modifiedPath + "_side");
            top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            if (tex.getPath().equals("block/smooth_quartz")) {
                side = top = bottom = new ResourceLocation(tex.getNamespace(), "block/quartz_block_bottom");
            }
            genWithSides.data((T) b.get(), side, bottom, top, tex);

        } else if (tex.getPath().contains("chalk_pillar") || tex.getPath().contains("purpur_pillar")) {
            top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
            genWithSides.data((T) b.get(), side, bottom, top, tex);

        } else if (tex.getPath().contains("sandstone")) {

            if (tex.getPath().contains("cut")) {
                modifiedPath = modifiedPath.replace("cut_", "");
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");

            } else if (tex.getPath().contains("chiseled")) {
                modifiedPath = modifiedPath.replace("chiseled_", "");
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");

            } else if (tex.getPath().contains("smooth")) {
                modifiedPath = modifiedPath.replace("smooth_", "");
                side = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
                top = bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");

            } else {
                top = new ResourceLocation(tex.getNamespace(), modifiedPath + "_top");
                bottom = new ResourceLocation(tex.getNamespace(), modifiedPath + "_bottom");
            }

            if (isMinecraftNamespace) {
                side = tex.getPath().contains("smooth") ? top : new ResourceLocation("minecraft", originalPath);
                top = new ResourceLocation("minecraft", modifiedPath + "_top");
                bottom = !tex.getPath().contains("smooth") && !tex.getPath().contains("cut") && !tex.getPath().contains("chiseled") ?
                        new ResourceLocation("minecraft", modifiedPath + "_bottom") : top;
            }

            genWithSides.data((T) b.get(), side, bottom, top, tex);
        } else {
            gen.data((T) b.get(), tex);
        }
    }

    private static String extractWoodType(ResourceLocation resourceLocation) {
        if (resourceLocation.getPath().contains("acacia")) {
            return "acacia";
        } else if (resourceLocation.getPath().contains("bamboo")) {
            return "bamboo";
        } else if (resourceLocation.getPath().contains("birch")) {
            return "birch";
        } else if (resourceLocation.getPath().contains("cherry")) {
            return "cherry";
        } else if (resourceLocation.getPath().contains("dark_oak")) {
            return "dark_oak";
        } else if (resourceLocation.getPath().contains("jungle")) {
            return "jungle";
        } else if (resourceLocation.getPath().contains("mangrove")) {
            return "mangrove";
        } else if (resourceLocation.getPath().contains("oak") && !resourceLocation.getPath().contains("dark")) {
            return "oak";
        } else if (resourceLocation.getPath().contains("spruce")) {
            return "spruce";
        } else {
            return null;
        }
    }

    @FunctionalInterface
    public interface BlockGenLayerWithSides<T extends Block> {
        void data(T block, ResourceLocation side, ResourceLocation top, ResourceLocation bottom, ResourceLocation baseBlock);
    }

    @FunctionalInterface
    public interface BlockGenWithBaseBlock<T extends Block> {
        void data(T block, ResourceLocation baseBlock);
    }
}