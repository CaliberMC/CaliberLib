package com.calibermc.caliberlib.mixin;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Either;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.InventoryMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(BlockModel.Deserializer.class)
public abstract class BlockModelDeserializerMixin {

    @Inject(method = "parseTextureLocationOrReference(Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;)Lcom/mojang/datafixers/util/Either;", at = @At("HEAD"), cancellable = true)
    private static void texture(ResourceLocation location, String name, CallbackInfoReturnable<Either<Material, String>> cir) {
        if (name.contains("$texture")) {
            cir.setReturnValue(Either.left(new Material(InventoryMenu.BLOCK_ATLAS, MissingTextureAtlasSprite.getLocation())));
        }
    }
}