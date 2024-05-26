package com.calibermc.caliberlib.dynamicpack;

import com.calibermc.caliberlib.CaliberLib;
import com.calibermc.caliberlib.block.management.BlockManager;
import com.calibermc.caliberlib.data.ModBlockFamily;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.datafixers.util.Pair;
import net.mehvahdjukaar.moonlight.api.events.AfterLanguageLoadEvent;
import net.mehvahdjukaar.moonlight.api.resources.RPUtils;
import net.mehvahdjukaar.moonlight.api.resources.ResType;
import net.mehvahdjukaar.moonlight.api.resources.StaticResource;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.util.Utils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;


public class ClientDynamicResourcesHandler extends DynClientResourcesGenerator {

    public static ClientDynamicResourcesHandler registerResourceHandler(String modId) {
        ClientDynamicResourcesHandler resourcesHandler = new ClientDynamicResourcesHandler(modId);
        resourcesHandler.register();
        return resourcesHandler;
    }

    public ClientDynamicResourcesHandler(String modId) {
        super(new DynamicTexturePack(new ResourceLocation(modId, "generated_pack")));
    }

    @Override
    public Logger getLogger() {
        return CaliberLib.LOGGER;
    }

    @Override
    public boolean dependsOnLoadedPacks() {
        return true;
    }

    @Override
    public void regenerateDynamicAssets(ResourceManager manager) {
        if (BlockManager.BLOCK_MANAGERS.containsKey(this.modId)) {
            for (BlockManager blockManager : BlockManager.BLOCK_MANAGERS.get(this.modId)) {
                for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : blockManager.getBlocks().entrySet()) {
                    ModBlockFamily.Variant variant = e.getKey().variant;
                    try {
                        var blockState = StaticResource.getOrFail(manager, ResType.BLOCKSTATES.getPath(new ResourceLocation(CaliberLib.MOD_ID, "template_%s".formatted(variant.equals(ModBlockFamily.Variant.BASE) ? "base" : e.getKey().variant()))));
                        var itemModel = StaticResource.getOrFail(manager, ResType.ITEM_MODELS.getPath(new ResourceLocation(CaliberLib.MOD_ID, "template_base")));
                        var itemModel2d = StaticResource.getOrFail(manager, ResType.ITEM_MODELS.getPath(new ResourceLocation(CaliberLib.MOD_ID, "template_2d")));

                        ResourceLocation id = Utils.getID(e.getValue().getSecond());
                        String modelId = new ResourceLocation(id.getNamespace(), "block/" + id.getPath()).toString();

                        String finalModelId1 = modelId;
                        this.addSimilarJsonResource(manager, blockState,
                                text -> genModelByUsingBS(text, blockManager, manager, id, e.getKey().variant(), finalModelId1),
                                name -> id.getPath() + ".json");

                        if (variant.equals(ModBlockFamily.Variant.DOOR) || variant.equals(ModBlockFamily.Variant.TALL_DOOR)
                                || variant.equals(ModBlockFamily.Variant.WALL_HANGING_SIGN) || variant.equals(ModBlockFamily.Variant.SIGN)) {
                            String textureId = new ResourceLocation(id.getNamespace(), "item/" + id.getPath()).toString();
                            this.addSimilarJsonResource(manager, itemModel2d,
                                    text -> text.replace("$texture", textureId),
                                    name -> id.getPath() + ".json");
                        } else {
                            if (variant.equals(ModBlockFamily.Variant.ARCH)) {
                                modelId += "_trim_2";
                            }

                            if (variant.equals(ModBlockFamily.Variant.CORNER)
                                    || variant.equals(ModBlockFamily.Variant.CORNER_SLAB)
                                    || variant.equals(ModBlockFamily.Variant.CORNER_SLAB_VERTICAL)
                                    || variant.equals(ModBlockFamily.Variant.EIGHTH)
                                    || variant.equals(ModBlockFamily.Variant.PILLAR)
                                    || variant.equals(ModBlockFamily.Variant.QUARTER)
                                    || variant.equals(ModBlockFamily.Variant.QUARTER_VERTICAL)) {
                                modelId += "_layer_3";
                            }

                            //                if (variant.equals(ModBlockFamily.Variant.SLAB) || variant.equals(ModBlockFamily.Variant.SLAB_VERTICAL)) {
                            //                    parentName += "_layer_4";
                            //                }

                            if (variant.equals(ModBlockFamily.Variant.LAYER)
                                    || variant.equals(ModBlockFamily.Variant.LAYER_VERTICAL)
                                    || variant.equals(ModBlockFamily.Variant.BEAM_LINTEL)
                                    || variant.equals(ModBlockFamily.Variant.DOOR_FRAME_LINTEL)
                                    || variant.equals(ModBlockFamily.Variant.BEAM_POSTS)
                                    || variant.equals(ModBlockFamily.Variant.DOOR_FRAME)
                                    || variant.equals(ModBlockFamily.Variant.BEAM_HORIZONTAL)
                                    || variant.equals(ModBlockFamily.Variant.BEAM_DIAGONAL)) {
                                modelId += "_1";
                            }

                            if (variant.equals(ModBlockFamily.Variant.BEAM_VERTICAL)) {
                                modelId += "_3";
                            }

                            if (variant.equals(ModBlockFamily.Variant.BUTTON)
                                    || variant.equals(ModBlockFamily.Variant.FENCE)
                                    || variant.equals(ModBlockFamily.Variant.WALL)
                                    || variant.equals(ModBlockFamily.Variant.ROOF_67)) {
                                modelId += "_inventory";
                                var blockModel = StaticResource.getOrFail(manager, ResType.BLOCK_MODELS.getPath(new ResourceLocation(CaliberLib.MOD_ID, "template_%s_inventory".formatted(e.getKey().variant()))));
                                this.addSimilarJsonResource(manager, blockModel,
                                        text -> textures(text, manager, blockManager),
                                        name -> name.replace("template_%s".formatted(e.getKey().variant()), id.getPath()));

                            }

                            if (variant.equals(ModBlockFamily.Variant.TRAPDOOR)) {
                                modelId += "_bottom";
                            }
                            String finalModelId = modelId;
                            this.addSimilarJsonResource(manager, itemModel,
                                    text -> text.replace("$block", finalModelId),
                                    name -> id.getPath() + ".json");
                        }

                    } catch (Exception ex) {
                        CaliberLib.LOGGER.error("Failed to generate assets for {}", e.getValue(), ex);
                    }
                }
            }
        }
    }


    public String genModelByUsingBS(String text, BlockManager blockManager, ResourceManager manager, ResourceLocation id, String variant, String modelId) {
        List<String> registeredModels = Lists.newArrayList();
        try {
            JsonObject parser = JsonParser.parseString(text).getAsJsonObject();
            if (parser.has("variants")) {
                JsonObject variants = parser.getAsJsonObject("variants");
                for (Map.Entry<String, JsonElement> e : variants.entrySet()) {
                    for (Map.Entry<String, JsonElement> e1 : e.getValue().getAsJsonObject().entrySet()) {
                        if (e1.getKey().equals("model")) {
                            this.genModel(e1.getValue().getAsString(), blockManager, manager, id, variant, registeredModels);
                        }
                    }
                }
            } else {
                JsonArray variants = parser.getAsJsonArray("multipart");
                for (JsonElement jsonElement : variants) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject("apply");
                    if (jsonObject.has("model")) {
                        this.genModel(jsonObject.get("model").getAsString(), blockManager, manager, id, variant, registeredModels);
                    }
                }
            }
        } catch (Throwable e) {
            e.fillInStackTrace();
        }
        String t = text.replace("$block", modelId.replace("_" + variant, ""));
        return t;
    }


    public void genModel(String text, BlockManager blockManager, ResourceManager manager, ResourceLocation id, String variant, List<String> registeredModels) {
        String model = text;
        ResourceLocation modelId1 = new ResourceLocation(CaliberLib.MOD_ID, "template");
        model = model.replace("$block", modelId1.toString());

        try {
            var blockModel = StaticResource.getOrFail(manager, ResType.BLOCK_MODELS.getPath(new ResourceLocation(model)));

            this.addSimilarJsonResource(manager, blockModel,
                    mText -> textures(mText, manager, blockManager),
                    name -> {
                        String f = name.replace(modelId1.getPath(), id.getPath().replace("_" + variant, ""));
                        if (variant.isEmpty()) {
                            f = name.replace("_base", "").replace(modelId1.getPath(), id.getPath());
                        }
                        registeredModels.add(f);
                        return f;
                    });
        } catch (Throwable t) {
            t.fillInStackTrace();
        }
    }

    public static String textures(String text, ResourceManager manager, BlockManager blockManager) {
        ResourceLocation texture;
        try {
            texture = RPUtils.findFirstBlockTextureLocation(manager, blockManager.mainChild());
        } catch (FileNotFoundException e) {
            e.fillInStackTrace();
            texture = new ResourceLocation("not_found");
        }
        String originalPath = texture.getPath();
        String modifiedPath = texture.getPath();
        ResourceLocation top = texture, side = texture, bottom = texture;
        if (texture.getPath().contains("_wood") && !texture.getPath().contains("stained") || texture.getPath().contains("_hyphae")) {
            String replacement = texture.getPath().contains("_wood") ? "_wood" : "_hyphae";
            String newTexture = texture.getPath().contains("_wood") ? "_log" : "_stem";
            texture = new ResourceLocation(texture.getNamespace(), texture.getPath().replace(replacement, newTexture));
        } else if (texture.getPath().contains("bamboo_block") && !texture.getPath().contains("stained")) {
            texture = new ResourceLocation(texture.getNamespace(), "block/stripped_bamboo_block");
        } else if (texture.getPath().contains("tudor")) {
            String woodType = extractWoodType(texture);
            String prefix = texture.getPath().contains("stained") ? "stained_" : "";
            top = bottom = new ResourceLocation(texture.getNamespace(), "block/" + prefix + woodType + "_boards");
        } else if (texture.getPath().equals("block/basalt") || texture.getPath().equals("block/polished_basalt") || (texture.getPath().contains("quartz") && !texture.getPath().contains("brick"))) {
            side = new ResourceLocation(texture.getNamespace(), modifiedPath + "_side");
            top = bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");
            if (texture.getPath().equals("block/smooth_quartz")) {
                side = top = bottom = new ResourceLocation(texture.getNamespace(), "block/quartz_block_bottom");
            }
        } else if (texture.getPath().contains("chalk_pillar") || texture.getPath().contains("purpur_pillar")) {
            top = bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");
        } else if (texture.getPath().contains("sandstone")) {

            if (texture.getPath().contains("cut")) {
                modifiedPath = modifiedPath.replace("cut_", "");
                top = bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");

            } else if (texture.getPath().contains("chiseled")) {
                modifiedPath = modifiedPath.replace("chiseled_", "");
                top = bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");

            } else if (texture.getPath().contains("smooth")) {
                modifiedPath = modifiedPath.replace("smooth_", "");
                side = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");
                top = bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");

            } else {
                top = new ResourceLocation(texture.getNamespace(), modifiedPath + "_top");
                bottom = new ResourceLocation(texture.getNamespace(), modifiedPath + "_bottom");
            }

            if (texture.getNamespace().equals("minecraft")) {
                side = texture.getPath().contains("smooth") ? top : new ResourceLocation("minecraft", originalPath);
                top = new ResourceLocation("minecraft", modifiedPath + "_top");
                bottom = !texture.getPath().contains("smooth") && !texture.getPath().contains("cut") && !texture.getPath().contains("chiseled") ?
                        new ResourceLocation("minecraft", modifiedPath + "_bottom") : top;
            }
        }
        text = text.replace("$texture", texture.toString())
                .replace("$top", top.toString())
                .replace("$side", side.toString())
                .replace("$bottom", bottom.toString());

        return text;
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


    @Override
    public void addDynamicTranslations(AfterLanguageLoadEvent lang) {
        if (BlockManager.BLOCK_MANAGERS.containsKey(this.modId)) {
            for (BlockManager b : BlockManager.BLOCK_MANAGERS.get(this.modId)) {
                for (Map.Entry<BlockManager.BlockAdditional, Pair<ResourceLocation, Supplier<Block>>> e : b.getBlocks().entrySet()) {
                    String base = lang.getEntry("block_type.caliber.block");
                    if (base != null) {
                        String typeName = lang.getEntry(b.getTranslationKey());
                        if (typeName != null) {
                            lang.addEntry(e.getValue().getSecond().get().getDescriptionId(), String.format(base, typeName, e.getKey().variant.equals(ModBlockFamily.Variant.BASE) ? "" : " " + StringUtils.capitalize(e.getKey().variant().replace("_", " "))));
                        }
                    }
                }

            }
        }
    }
}