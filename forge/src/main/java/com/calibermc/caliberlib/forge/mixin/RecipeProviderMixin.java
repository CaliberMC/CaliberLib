package com.calibermc.caliberlib.forge.mixin;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraftforge.data.loading.DatagenModLoader;
import org.joml.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeProvider.class)
public class RecipeProviderMixin {

    @Inject(at = @At("TAIL"), method = "getName", cancellable = true)
    private void patch$getName(CallbackInfoReturnable<String> cir) {
        if (DatagenModLoader.isRunningDataGen()) {
            cir.setReturnValue(cir.getReturnValue() + " " + Random.newSeed());
        }
    }
}
