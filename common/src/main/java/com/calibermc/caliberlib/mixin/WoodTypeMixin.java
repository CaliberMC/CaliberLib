package com.calibermc.caliberlib.mixin;

import com.calibermc.caliberlib.block.properties.ModWoodType;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;
import java.util.stream.Stream;

@Mixin(WoodType.class)
public class WoodTypeMixin {

    @Inject(at = @At("RETURN"), method = "values()Ljava/util/stream/Stream;", cancellable = true)
    private static void patch$values(CallbackInfoReturnable<Stream<WoodType>> cir) {
        Set<WoodType> set = ModWoodType.getWoodTypes();
        set.addAll(cir.getReturnValue().toList());
        cir.setReturnValue(set.stream());
    }
}
