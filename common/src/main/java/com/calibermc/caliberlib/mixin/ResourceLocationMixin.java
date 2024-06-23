package com.calibermc.caliberlib.mixin;

import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ResourceLocation.class)
public abstract class ResourceLocationMixin {

    @Inject(method = "isValidPath(Ljava/lang/String;)Z", at = @At("HEAD"), cancellable = true)
    private static void block(String path, CallbackInfoReturnable<Boolean> cir) {
        if (path.contains("$block")) {
            cir.setReturnValue(true);
        }
    }
}
