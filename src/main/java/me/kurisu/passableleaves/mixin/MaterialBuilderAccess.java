package me.kurisu.passableleaves.mixin;

import net.minecraft.block.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Material.Builder.class)
public interface MaterialBuilderAccess {
    @Invoker("burnable")
    Material.Builder setBurnable();

    @Invoker("lightPassesThrough")
    Material.Builder setLightPassesThrough();

    @Invoker("destroyedByPiston")
    Material.Builder setDestroyedByPiston();
}
