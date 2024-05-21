package com.calibermc.caliberlib.forge;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.calibermc.caliberlib.forge.block.ModBlockHelperForge;
import com.calibermc.caliberlib.forge.util.AdditionalDataGenForge;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mod(CaliberLib.MOD_ID)
public class CaliberLibForge {
    public CaliberLibForge() {
        // Submit our event bus to let architectury register our content at the right time
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        CaliberLib.init();
        CaliberLibForge.genData();
    }

    public static void genData() {
        for (List<BlockManager> value : BlockManager.BLOCK_MANAGERS.values()) {
            for (BlockManager blockManager : value) {
                BlockManager.BlockAdditional base = blockManager.getByVariant(ModBlockFamily.Variant.BASE);
                if (base != null && !base.skipRegister) {
                    addVariant(base, (b) -> {});
                }
                for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : blockManager.getBlocks().entrySet()) {
                    if (e.getKey().variant != ModBlockFamily.Variant.BASE && e.getKey().variant.caliberIsLoaded()) {
                        Supplier<Block> blockSupplier = e.getKey().textureFrom;
                        switch (e.getKey().variant) {
                            case ARCH ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ARCH.apply(blockSupplier)));
                            case ARCH_HALF ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ARCH_HALF.apply(blockSupplier)));
                            case ARCH_LARGE ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ARCH_LARGE.apply(blockSupplier)));
                            case ARCH_LARGE_HALF ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ARCH_LARGE_HALF.apply(blockSupplier)));
                            case ARROWSLIT ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ARROWSLIT.apply(blockSupplier)));
                            case BALUSTRADE ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BALUSTRADE.apply(blockSupplier)));
                            case BUTTON ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BUTTON.apply(blockSupplier)));
                            case BEAM_DIAGONAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BEAM_DIAGONAL.apply(blockSupplier)));
                            case BEAM_HORIZONTAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BEAM_HORIZONTAL.apply(blockSupplier)));
                            case BEAM_LINTEL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BEAM_LINTEL.apply(blockSupplier)));
                            case BEAM_POSTS ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BEAM_POSTS.apply(blockSupplier)));
                            case BEAM_VERTICAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.BEAM_VERTICAL.apply(blockSupplier)));
                            case CAPITAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.CAPITAL.apply(blockSupplier)));
                            case CORNER ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.CORNER.apply(blockSupplier)));
                            case CORNER_SLAB ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.CORNER_SLAB.apply(blockSupplier)));
                            case CORNER_SLAB_VERTICAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.CORNER_SLAB_VERTICAL.apply(blockSupplier)));
                            case DOOR ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.DOOR.apply(blockSupplier)));
                            case DOOR_FRAME ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.DOOR_FRAME.apply(blockSupplier)));
                            case DOOR_FRAME_LINTEL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.DOOR_FRAME_LINTEL.apply(blockSupplier)));
                            case EIGHTH ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.EIGHTH.apply(blockSupplier)));
                            case FENCE ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.FENCE.apply(blockSupplier)));
                            case FENCE_GATE ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.FENCE_GATE.apply(blockSupplier)));
                            case LAYER ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.LAYER.apply(blockSupplier)));
                            case LAYER_VERTICAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.LAYER_VERTICAL.apply(blockSupplier)));
                            case PILLAR ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.PILLAR.apply(blockSupplier)));
                            case PRESSURE_PLATE ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.PRESSURE_PLATE.apply(blockSupplier)));
                            case QUARTER ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.QUARTER.apply(blockSupplier)));
                            case QUARTER_VERTICAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.VERTICAL_QUARTER.apply(blockSupplier)));
                            case ROOF_22 ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ROOF_22.apply(blockSupplier)));
                            case ROOF_45 ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ROOF_45.apply(blockSupplier)));
                            case ROOF_67 ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ROOF_67.apply(blockSupplier)));
                            case ROOF_PEAK ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.ROOF_PEAK.apply(blockSupplier)));
                            case SIGN ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.SIGN));
                            case HANGING_SIGN ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.HANGING_SIGN));
                            case SLAB ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.SLAB.apply(blockSupplier)));
                             case SLAB_VERTICAL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.SLAB_VERTICAL.apply(blockSupplier)));
                            case TALL_DOOR ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.TALL_DOOR.apply(blockSupplier)));
                            case STAIRS ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.STAIRS.apply(blockSupplier)));
                            case TRAPDOOR ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.TRAP_DOOR.apply(blockSupplier)));
                            case WALL ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.WALL.apply(blockSupplier)));
//                            case WALL_SIGN ->
//                                    addVariant(e.getKey(), b -> {});
//                            case WALL_HANGING_SIGN ->
//                                    addVariant(e.getKey(), b -> {});
                            case WINDOW ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.WINDOW.apply("window", blockSupplier)));
                            case WINDOW_HALF ->
                                    addVariant(e.getKey(), (b) -> b.stateGen(ModBlockHelperForge.WINDOW_HALF.apply("window_half", blockSupplier)));
                        }
                    }

                }
            }
        }
    }
    
    
    public static void addVariant(BlockManager.BlockAdditional key, Consumer<AdditionalDataGenForge> forge) {
        BlockManager.DATA_GEN_MAP.put(key, (b) -> {
            if (b instanceof AdditionalDataGenForge f) {
                forge.accept(f);
            }
        });
    }
}
