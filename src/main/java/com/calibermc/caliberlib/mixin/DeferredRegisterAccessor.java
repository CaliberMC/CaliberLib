package com.calibermc.caliberlib.mixin;

import net.neoforged.neoforge.registries.DeferredRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = DeferredRegister.class, remap = false)
public interface DeferredRegisterAccessor {

    @Accessor("namespace")
    String modid();
}
