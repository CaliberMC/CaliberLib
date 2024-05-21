package com.calibermc.caliberlib.forge.mixin;

import com.calibermc.caliberlib.forge.util.DataGenUtil;
import net.minecraft.data.loot.LootTableProvider;
import org.joml.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootTableProvider.class)
public class LootTableProviderMixin {

    @Inject(at = @At("TAIL"), method = "getName", cancellable = true)
    private void patch$getName(CallbackInfoReturnable<String> cir) {
        if (DataGenUtil.isDataGen) {
            cir.setReturnValue(cir.getReturnValue() + " " + Random.newSeed());
        }
    }
}
