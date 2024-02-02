package com.calibermc.caliberlib.mixin;

import net.minecraft.data.recipes.RecipeProvider;
import org.joml.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeProvider.class)
public class RecipeProviderMixin {

    @Inject(at = @At("TAIL"), method = "getName", cancellable = true)
    private void patch$getName(CallbackInfoReturnable<String> cir) {
        cir.setReturnValue(cir.getReturnValue() + " " + Random.newSeed());
    }

}
