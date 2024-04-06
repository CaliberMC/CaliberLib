package com.calibermc.caliberlib.data.datagen;

import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModItemTagProvider extends ItemTagsProvider {

    protected final String modid;

    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pItemTags, String modid, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pItemTags, modid, existingFileHelper);
        this.modid = modid;
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        for (BlockManager blockManager : BlockManager.BLOCK_MANAGERS.get(this.modid)) {
            for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : blockManager.getBlocks().entrySet()) {
                ModBlockFamily.Variant variant = e.getKey().variant;
                String blockName = e.getValue().getFirst().getPath();
                BlockSetType blockSetType = blockManager.blockType();

                if (blockName.contains("crimson") || blockName.contains("warped")) {
                    this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(e.getValue().getSecond().get().asItem());
                }

                if (blockName.contains("thatch")) {
                    this.tag(ItemTags.DAMPENS_VIBRATIONS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.BASE)) {
                    if (blockName.contains("cobbled")) {
                        this.tag(ItemTags.STONE_CRAFTING_MATERIALS).add(e.getValue().getSecond().get().asItem());
                    } else if (blockName.contains("limestone_bricks")) {
                        this.tag(ItemTags.STONE_BRICKS).add(e.getValue().getSecond().get().asItem());
                    } else if (blockName.contains("log") || (blockName.contains("wood"))) {
                        this.tag(ItemTags.LOGS_THAT_BURN).add(e.getValue().getSecond().get().asItem());
                    } else if (blockName.contains("planks")) {
                        this.tag(ItemTags.PLANKS).add(e.getValue().getSecond().get().asItem());
                    }
                }

                if (variant.equals(ModBlockFamily.Variant.DOOR)) {
                    this.tag(ItemTags.WOODEN_DOORS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.FENCE)) {
                    this.tag(ItemTags.WOODEN_FENCES).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.FENCE_GATE)) {
                    this.tag(ItemTags.WOODEN_FENCES).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.HANGING_SIGN)) {
                    this.tag(ItemTags.HANGING_SIGNS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.PRESSURE_PLATE)) {
                    this.tag(ItemTags.WOODEN_PRESSURE_PLATES);
                }

                if (variant.equals(ModBlockFamily.Variant.SIGN)) {
                    this.tag(ItemTags.SIGNS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.SLAB)) {
                    this.tag(ItemTags.SLABS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.STAIRS)) {
                    this.tag(ItemTags.STAIRS).add(e.getValue().getSecond().get().asItem());
                }

                if (variant.equals(ModBlockFamily.Variant.TRAPDOOR)) {
                    this.tag(ItemTags.TRAPDOORS).add(e.getValue().getSecond().get().asItem());
                }
            }
        }

        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> {
                    ResourceLocation registryName = ForgeRegistries.BLOCKS.getKey(block);
                    return BlockManager.ALL_BLOCKS.stream().map(Supplier::get).noneMatch(b -> b.equals(block))
                            && registryName != null && registryName.getNamespace().equals(this.modId);
                })
                .forEach(block -> {
                    String itemName = Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block)).getPath();

                    if (itemName.contains("crimson") || itemName.contains("warped")) {
                        this.tag(ItemTags.NON_FLAMMABLE_WOOD).add(block.asItem());
                    }

                    if (itemName.contains("leaves")) {
                        this.tag(ItemTags.LEAVES).add(block.asItem());
                    }

                    if (itemName.contains("sand") && !itemName.contains("stone")) {
                        this.tag(ItemTags.SAND).add(block.asItem());
                        this.tag(ItemTags.SMELTS_TO_GLASS).add(block.asItem());
                        this.tag(Tags.Items.SAND).add(block.asItem());
                    }

                    if (itemName.contains("ingot")) {
                        this.tag(ItemTags.TRIM_MATERIALS).add(block.asItem());
                    }

                    if (itemName.contains("ore")) {
                        this.tag(Tags.Items.ORES).add(block.asItem());
                        if (itemName.contains("deepslate")) {
                            this.tag(Tags.Items.ORES_IN_GROUND_DEEPSLATE).add(block.asItem());
                        } else if (itemName.contains("nether")) {
                            this.tag(Tags.Items.ORES_IN_GROUND_NETHERRACK).add(block.asItem());
                        } else {
                            this.tag(Tags.Items.ORES_IN_GROUND_STONE).add(block.asItem());
                        }
                    }
                });

        // Item Tags
        for (Map.Entry<ResourceKey<Item>, Item> resourceKeyItemEntry : ForgeRegistries.ITEMS.getEntries()) {
            // Check if the item's registry name is in the MOD_ID namespace
            ResourceLocation registryName = resourceKeyItemEntry.getKey().location();
            String itemName = registryKey.toString();
            if (registryName != null && this.modId.equals(registryName.getNamespace())) {
                Item item = resourceKeyItemEntry.getValue();
                Block block = Block.byItem(item);

                if (item instanceof SwordItem) {
                    this.tag(ItemTags.SWORDS).add(item);
                }

                if (item instanceof PickaxeItem) {
                    this.tag(ItemTags.PICKAXES).add(item);
                }

                if (item instanceof ShovelItem) {
                    this.tag(ItemTags.SHOVELS).add(item);
                }

                if (item instanceof HoeItem) {
                    this.tag(ItemTags.HOES).add(item);

                }
            }
        }

    }
}

