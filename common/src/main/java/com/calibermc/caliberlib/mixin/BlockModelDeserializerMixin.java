package com.calibermc.caliberlib.mixin;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(BlockModel.Deserializer.class)
public abstract class BlockModelDeserializerMixin {

    @Shadow
    protected static Either<Material, String> parseTextureLocationOrReference(ResourceLocation pLocation, String pName) {
        return null;
    }

    @Inject(method = "getTextureMap", at = @At("HEAD"), cancellable = true)
    public void bruh(JsonObject pJson, CallbackInfoReturnable<Map<String, Either<Material, String>>> cir) {
        if (pJson.has("caliber")) {
            ResourceLocation resourcelocation = TextureAtlas.LOCATION_BLOCKS;
            Map<String, Either<Material, String>> map = Maps.newHashMap();
            if (pJson.has("textures")) {
                JsonObject jsonobject = GsonHelper.getAsJsonObject(pJson, "textures");
                String s = "minecraft:block/acacia_planks";

                for (Map.Entry<String, JsonElement> entry : jsonobject.entrySet()) {
                    map.put(entry.getKey(), parseTextureLocationOrReference(resourcelocation, entry.getValue().getAsString()));
                }

                map.put("top", parseTextureLocationOrReference(resourcelocation, s));
                map.put("bottom", parseTextureLocationOrReference(resourcelocation, s));
                map.put("side", parseTextureLocationOrReference(resourcelocation, s));
            }

            cir.setReturnValue(map);
        }
    }
}
